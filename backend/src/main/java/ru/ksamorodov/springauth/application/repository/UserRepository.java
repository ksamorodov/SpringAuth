package ru.ksamorodov.springauth.application.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import ru.ksamorodov.springauth.application.dao.UserPrincipal;

import java.util.List;
import java.util.Optional;

import static ru.ksamorodov.springauth.adapters.db.jooq.tables.AuthUser.AUTH_USER;

@Component
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dsl;

    public Optional<UserPrincipal> findByUsername(String username) {
        return dsl.select(AUTH_USER.USERNAME, AUTH_USER.PASSWORD_HASH).from(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username)).fetchOptionalInto(UserPrincipal.class);
    }

    public List<UserPrincipal> getAllUsers() {
        return dsl.select(AUTH_USER.USERNAME, AUTH_USER.PASSWORD_HASH).from(AUTH_USER)
                .fetchInto(UserPrincipal.class);
    }
}
