package ru.ksamorodov.springauth.application.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private UUID id;
    private String username;
    private String password;
    private String passwordHash;
    private boolean blockedAt;
    private BigDecimal wrongLoginCount;
    private boolean isValidPassword;
    private boolean isTemporaryPassword;
    private Role role;

    @JsonIgnore
    private Authority authority = new Authority(role);

    public static class Authority implements GrantedAuthority {
        private Role role;

        Authority(Role role) {
            super();
            this.role = role;
        }
        @Override
        public String getAuthority() {
            return role.toString();
        }
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return blockedAt == false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
