package ru.ksamorodov.springauth.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;
import ru.ksamorodov.springauth.application.repository.UserRepository;
import ru.ksamorodov.springauth.application.service.FileUtilService;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Configuration
    public static class AppSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;
        private final PersistentTokenRepository persistentTokenRepository;
        private final String[] allowedOrigins;
        private final Duration rememberMeTokenValidityDuration;
        @Autowired
        private UserRepository userRepository;


        public AppSecurityConfigurationAdapter(UserDetailsService userDetailsService,
                                               PersistentTokenRepository persistentTokenRepository,
                                               @Value("${app.allowedOrigins}") String[] allowedOrigins,
                                               @Value("${app.tokens.remember-me-token-validity-duration}") Duration rememberMeTokenValidityDuration) {
            this.userDetailsService = userDetailsService;
            this.persistentTokenRepository = persistentTokenRepository;
            this.allowedOrigins = allowedOrigins;
            this.rememberMeTokenValidityDuration = rememberMeTokenValidityDuration;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
//            FileUtilService.test();
            List<UserPrincipal> read = FileUtilService.read();
            if (read.isEmpty()) {
                String json = "[ {\n" +
                        "  \"id\" : \"d81827e3-ff51-4419-bbad-950ab7d7e7fd\",\n" +
                        "  \"username\" : \"admin\",\n" +
                        "  \"password\" : null,\n" +
                        "  \"passwordHash\" : \"$2a$10$qnfz5DIUih2sBDP8OxQXq.2/PCwUHVQ/0sLlCf7.EO1iSmTA0fuem\",\n" +
                        "  \"blockedAt\" : false,\n" +
                        "  \"wrongLoginCount\" : 0,\n" +
                        "  \"role\" : \"ADMIN\",\n" +
                        "  \"validPassword\" : false,\n" +
                        "  \"temporaryPassword\" : true\n" +
                        "} ]";
                FileUtils.writeStringToFile(new File("decryptedBD.txt"), json, "UTF-8");
                FileUtilService.cryptBd();
            } else {
                userRepository.insertAllUsers(read);
                FileUtilService.cryptBd();
            }

            http
                    .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

            http.cors().configurationSource(corsConfigurationSource(List.of(this.allowedOrigins)));
            http.csrf().disable();

            http.logout()
                    .permitAll()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .deleteCookies("JSESSIONID");

            http.rememberMe()
                    .userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository)
                    .tokenValiditySeconds((int) rememberMeTokenValidityDuration.toSeconds());

            http.formLogin()
                    .loginProcessingUrl("/auth/login")
                    .successHandler((request, response, authentication) -> {
                        userRepository.successLogin(authentication.getName());

                        FileUtilService.write(userRepository.getAllUsers());
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().println("{}");
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.getWriter().flush();
                    });
        }
    }

    static CorsConfigurationSource corsConfigurationSource(List<String> allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("x-requested-with");
        configuration.addAllowedHeader("x-xsrf-token");
        configuration.addAllowedHeader("x-auth-token");
        configuration.addAllowedHeader("Content-Type");
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "PATCH", "HEAD", "DELETE"));
        return firewall;
    }
}
