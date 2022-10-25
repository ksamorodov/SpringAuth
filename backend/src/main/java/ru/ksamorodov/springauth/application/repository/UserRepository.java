package ru.ksamorodov.springauth.application.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ksamorodov.springauth.application.dao.Role;
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
        return dsl.select(AUTH_USER.ID, AUTH_USER.USERNAME, AUTH_USER.FIRST_NAME, AUTH_USER.LAST_NAME, AUTH_USER.PASSWORD_HASH.as("password"), AUTH_USER.ROLE, AUTH_USER.BLOCKED_AT).from(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username)).fetchOptionalInto(UserPrincipal.class);
    }

    public List<UserPrincipal> getAllUsers() {
        return dsl.select(AUTH_USER.ID, AUTH_USER.USERNAME, AUTH_USER.FIRST_NAME, AUTH_USER.LAST_NAME, AUTH_USER.ROLE, AUTH_USER.BLOCKED_AT, AUTH_USER.IS_VALID_PASSWORD).from(AUTH_USER)
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
                .set(AUTH_USER.ROLE, Role.USER.toString())
                .set(AUTH_USER.PASSWORD_HASH, passwordEncoder.encode(user.getPassword())).execute();
    }

    public void blockUser(UUID id) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.BLOCKED_AT, LocalDateTime.now()).where(AUTH_USER.ID.eq(id)).execute();
    }

    public void unblockUser(UUID id) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.BLOCKED_AT, (LocalDateTime) null).where(AUTH_USER.ID.eq(id)).execute();
    }

    public void setPassword(UUID id, String password) {
        String pass = null;
        if (password != null && !password.isEmpty()) {
            pass = passwordEncoder.encode(password);
        }
        dsl.update(AUTH_USER)
                .set(AUTH_USER.PASSWORD_HASH, (String) pass).where(AUTH_USER.ID.eq(id))
                .execute();
    }

}
