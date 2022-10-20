package ru.ksamorodov.springauth.application.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.ksamorodov.springauth.adapters.db.jooq.tables.AuthUser.AUTH_USER;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final PasswordEncoder passwordEncoder;
    private final DSLContext dsl;

    public Optional<UserPrincipal> findByUsername(String username) {
        return dsl.select(AUTH_USER.ID, AUTH_USER.USERNAME, AUTH_USER.FIRST_NAME, AUTH_USER.LAST_NAME, AUTH_USER.PASSWORD_HASH.as("password")).from(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username)).fetchOptionalInto(UserPrincipal.class);
    }

    public List<UserPrincipal> getAllUsers() {
        return dsl.select(AUTH_USER.ID, AUTH_USER.USERNAME, AUTH_USER.FIRST_NAME, AUTH_USER.LAST_NAME, AUTH_USER.PASSWORD_HASH).from(AUTH_USER)
                .fetchInto(UserPrincipal.class);
    }


    public int deleteUser(UUID id) {
        return dsl.deleteFrom(AUTH_USER).where(AUTH_USER.ID.eq(id)).execute();
    }

    public int register(UserPrincipal user) {
        return dsl.insertInto(AUTH_USER)
                .set(AUTH_USER.ID, UUID.randomUUID())
                .set(AUTH_USER.USERNAME, user.getUsername())
                .set(AUTH_USER.FIRST_NAME, user.getFirstName())
                .set(AUTH_USER.LAST_NAME, user.getLastName())
                .set(AUTH_USER.CREATED_AT, LocalDateTime.now())
                .set(AUTH_USER.PASSWORD_HASH, passwordEncoder.encode(user.getPassword())).execute();
    }
}
