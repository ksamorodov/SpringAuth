package ru.springauth.application.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.springauth.application.dao.Role;
import ru.springauth.application.dao.UserPrincipal;
import ru.springauth.application.service.FileUtilService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.springauth.adapters.db.jooq.tables.AuthUser.AUTH_USER;

@Component
@RequiredArgsConstructor
public class UserRepository {
    private final PasswordEncoder passwordEncoder;
    private final DSLContext dsl;

    public Optional<UserPrincipal> findByUsername(String username) {
        wrongLogin(username);
        Optional<UserPrincipal> byUsername = dsl.select(AUTH_USER.ID, AUTH_USER.USERNAME, AUTH_USER.PASSWORD_HASH.as("password"), AUTH_USER.ROLE, AUTH_USER.IS_TEMPORARY_PASSWORD, AUTH_USER.IS_VALID_PASSWORD, AUTH_USER.BLOCKED_AT).from(AUTH_USER)
                .where(AUTH_USER.USERNAME.eq(username)).fetchOptionalInto(UserPrincipal.class);
        FileUtilService.write(getAllUsers());

        return byUsername;
    }

    public List<UserPrincipal> getAllUsers() {
        return dsl.select(AUTH_USER.ID, AUTH_USER.WRONG_LOGIN_COUNT, AUTH_USER.USERNAME, AUTH_USER.PASSWORD_HASH,  AUTH_USER.ROLE, AUTH_USER.BLOCKED_AT, AUTH_USER.IS_VALID_PASSWORD, AUTH_USER.IS_TEMPORARY_PASSWORD, AUTH_USER.WRONG_LOGIN_COUNT).from(AUTH_USER).fetchInto(UserPrincipal.class);
    }

    public void insertAllUsers(List<UserPrincipal> users) {
       try {
           dsl.truncate(AUTH_USER).cascade().execute();
           users.forEach(user -> dsl.insertInto(AUTH_USER)
                   .set(AUTH_USER.ID, user.getId())
                   .set(AUTH_USER.USERNAME, user.getUsername())
                   .set(AUTH_USER.PASSWORD_HASH, user.getPasswordHash())
                   .set(AUTH_USER.ROLE, user.getRole().toString())
                   .set(AUTH_USER.BLOCKED_AT, user.isBlockedAt())
                   .set(AUTH_USER.WRONG_LOGIN_COUNT, user.getWrongLoginCount())
                   .set(AUTH_USER.IS_VALID_PASSWORD, user.isValidPassword())
                   .set(AUTH_USER.IS_TEMPORARY_PASSWORD, user.isTemporaryPassword()).execute());

       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public boolean checkOldPassword(String username, String password) {
        Optional<UserPrincipal> byUsername = findByUsername(username);

        if (byUsername.isPresent()) {
            return passwordEncoder.matches(password, byUsername.get().getPassword());
        } else {
            return false;
        }
    }

    public int deleteUser(UUID id) {
        int execute = dsl.deleteFrom(AUTH_USER).where(AUTH_USER.ID.eq(id)).execute();
        FileUtilService.write(getAllUsers());
        return execute;
    }

    public int register(String username) {
        int execute = dsl.insertInto(AUTH_USER)
                .set(AUTH_USER.ID, UUID.randomUUID())
                .set(AUTH_USER.USERNAME, username)
                .set(AUTH_USER.IS_VALID_PASSWORD, true)
                .set(AUTH_USER.PASSWORD_HASH, passwordEncoder.encode(""))
                .set(AUTH_USER.ROLE, Role.USER.toString()).execute();

        FileUtilService.write(getAllUsers());

        return execute;
    }

    public void blockUser(UUID id) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.BLOCKED_AT, true).where(AUTH_USER.ID.eq(id)).execute();
        FileUtilService.write(getAllUsers());
    }

    public void blockUser(String username) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.BLOCKED_AT, true).where(AUTH_USER.USERNAME.eq(username)).execute();
        FileUtilService.write(getAllUsers());
    }

    public void unblockUser(UUID id) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.BLOCKED_AT, false)
                .set(AUTH_USER.WRONG_LOGIN_COUNT, BigDecimal.ZERO)
                .where(AUTH_USER.ID.eq(id)).execute();
        FileUtilService.write(getAllUsers());
    }

    public void setValidationPassword(UUID id, boolean value) {
        dsl.update(AUTH_USER)
                .set(AUTH_USER.IS_VALID_PASSWORD, value).where(AUTH_USER.ID.eq(id)).execute();
        FileUtilService.write(getAllUsers());
    }

    public void setPassword(UUID id, String password) {
        String pass = null;
        if (password != null && !password.isEmpty()) {
            pass = passwordEncoder.encode(password);
        }
        dsl.update(AUTH_USER)
                .set(AUTH_USER.PASSWORD_HASH, (String) pass)
                .set(AUTH_USER.IS_TEMPORARY_PASSWORD, false)
                .where(AUTH_USER.ID.eq(id))
                .execute();
        FileUtilService.write(getAllUsers());
    }

    public void wrongLogin(String username) {
        BigDecimal value = dsl.select(AUTH_USER.WRONG_LOGIN_COUNT).from(AUTH_USER).where(AUTH_USER.USERNAME.eq(username)).fetchOneInto(BigDecimal.class);
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        if (value.compareTo(BigDecimal.valueOf(3)) < 0) {
            value = value.add(BigDecimal.ONE);
        } else {
            blockUser(username);
        }
        dsl.update(AUTH_USER).set(AUTH_USER.WRONG_LOGIN_COUNT, value).where(AUTH_USER.USERNAME.eq(username)).execute();
    }

    public void successLogin(String username) {
        dsl.update(AUTH_USER).set(AUTH_USER.WRONG_LOGIN_COUNT, BigDecimal.ZERO).where(AUTH_USER.USERNAME.eq(username)).execute();
    }

}
