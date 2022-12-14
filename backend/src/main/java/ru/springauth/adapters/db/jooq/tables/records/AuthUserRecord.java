/*
 * This file is generated by jOOQ.
 */
package ru.springauth.adapters.db.jooq.tables.records;


import java.math.BigDecimal;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

import ru.springauth.adapters.db.jooq.tables.AuthUser;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthUserRecord extends UpdatableRecordImpl<AuthUserRecord> implements Record9<UUID, String, String, String, String, Boolean, Boolean, Boolean, BigDecimal> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.auth_user.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.auth_user.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.auth_user.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.auth_user.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.auth_user.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.auth_user.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.auth_user.password_reset_token</code>.
     */
    public void setPasswordResetToken(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.auth_user.password_reset_token</code>.
     */
    public String getPasswordResetToken() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.auth_user.role</code>.
     */
    public void setRole(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.auth_user.role</code>.
     */
    public String getRole() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.auth_user.is_valid_password</code>.
     */
    public void setIsValidPassword(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.auth_user.is_valid_password</code>.
     */
    public Boolean getIsValidPassword() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>public.auth_user.is_temporary_password</code>.
     */
    public void setIsTemporaryPassword(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.auth_user.is_temporary_password</code>.
     */
    public Boolean getIsTemporaryPassword() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>public.auth_user.blocked_at</code>.
     */
    public void setBlockedAt(Boolean value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.auth_user.blocked_at</code>.
     */
    public Boolean getBlockedAt() {
        return (Boolean) get(7);
    }

    /**
     * Setter for <code>public.auth_user.wrong_login_count</code>.
     */
    public void setWrongLoginCount(BigDecimal value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.auth_user.wrong_login_count</code>.
     */
    public BigDecimal getWrongLoginCount() {
        return (BigDecimal) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<UUID, String, String, String, String, Boolean, Boolean, Boolean, BigDecimal> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<UUID, String, String, String, String, Boolean, Boolean, Boolean, BigDecimal> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return AuthUser.AUTH_USER.ID;
    }

    @Override
    public Field<String> field2() {
        return AuthUser.AUTH_USER.USERNAME;
    }

    @Override
    public Field<String> field3() {
        return AuthUser.AUTH_USER.PASSWORD_HASH;
    }

    @Override
    public Field<String> field4() {
        return AuthUser.AUTH_USER.PASSWORD_RESET_TOKEN;
    }

    @Override
    public Field<String> field5() {
        return AuthUser.AUTH_USER.ROLE;
    }

    @Override
    public Field<Boolean> field6() {
        return AuthUser.AUTH_USER.IS_VALID_PASSWORD;
    }

    @Override
    public Field<Boolean> field7() {
        return AuthUser.AUTH_USER.IS_TEMPORARY_PASSWORD;
    }

    @Override
    public Field<Boolean> field8() {
        return AuthUser.AUTH_USER.BLOCKED_AT;
    }

    @Override
    public Field<BigDecimal> field9() {
        return AuthUser.AUTH_USER.WRONG_LOGIN_COUNT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getUsername();
    }

    @Override
    public String component3() {
        return getPasswordHash();
    }

    @Override
    public String component4() {
        return getPasswordResetToken();
    }

    @Override
    public String component5() {
        return getRole();
    }

    @Override
    public Boolean component6() {
        return getIsValidPassword();
    }

    @Override
    public Boolean component7() {
        return getIsTemporaryPassword();
    }

    @Override
    public Boolean component8() {
        return getBlockedAt();
    }

    @Override
    public BigDecimal component9() {
        return getWrongLoginCount();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getUsername();
    }

    @Override
    public String value3() {
        return getPasswordHash();
    }

    @Override
    public String value4() {
        return getPasswordResetToken();
    }

    @Override
    public String value5() {
        return getRole();
    }

    @Override
    public Boolean value6() {
        return getIsValidPassword();
    }

    @Override
    public Boolean value7() {
        return getIsTemporaryPassword();
    }

    @Override
    public Boolean value8() {
        return getBlockedAt();
    }

    @Override
    public BigDecimal value9() {
        return getWrongLoginCount();
    }

    @Override
    public AuthUserRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public AuthUserRecord value2(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public AuthUserRecord value3(String value) {
        setPasswordHash(value);
        return this;
    }

    @Override
    public AuthUserRecord value4(String value) {
        setPasswordResetToken(value);
        return this;
    }

    @Override
    public AuthUserRecord value5(String value) {
        setRole(value);
        return this;
    }

    @Override
    public AuthUserRecord value6(Boolean value) {
        setIsValidPassword(value);
        return this;
    }

    @Override
    public AuthUserRecord value7(Boolean value) {
        setIsTemporaryPassword(value);
        return this;
    }

    @Override
    public AuthUserRecord value8(Boolean value) {
        setBlockedAt(value);
        return this;
    }

    @Override
    public AuthUserRecord value9(BigDecimal value) {
        setWrongLoginCount(value);
        return this;
    }

    @Override
    public AuthUserRecord values(UUID value1, String value2, String value3, String value4, String value5, Boolean value6, Boolean value7, Boolean value8, BigDecimal value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthUserRecord
     */
    public AuthUserRecord() {
        super(AuthUser.AUTH_USER);
    }

    /**
     * Create a detached, initialised AuthUserRecord
     */
    public AuthUserRecord(UUID id, String username, String passwordHash, String passwordResetToken, String role, Boolean isValidPassword, Boolean isTemporaryPassword, Boolean blockedAt, BigDecimal wrongLoginCount) {
        super(AuthUser.AUTH_USER);

        setId(id);
        setUsername(username);
        setPasswordHash(passwordHash);
        setPasswordResetToken(passwordResetToken);
        setRole(role);
        setIsValidPassword(isValidPassword);
        setIsTemporaryPassword(isTemporaryPassword);
        setBlockedAt(blockedAt);
        setWrongLoginCount(wrongLoginCount);
    }
}
