/*
 * This file is generated by jOOQ.
 */
package ru.ksamorodov.springauth.adapters.db.jooq.tables.records;


import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import ru.ksamorodov.springauth.adapters.db.jooq.tables.AuthUser;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthUserRecord extends UpdatableRecordImpl<AuthUserRecord> implements Record6<UUID, String, String, String, String, LocalDateTime> {

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
     * Setter for <code>public.auth_user.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.auth_user.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.auth_user.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.auth_user.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.auth_user.password_reset_token</code>.
     */
    public void setPasswordResetToken(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.auth_user.password_reset_token</code>.
     */
    public String getPasswordResetToken() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.auth_user.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.auth_user.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, String, String, String, String, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<UUID, String, String, String, String, LocalDateTime> valuesRow() {
        return (Row6) super.valuesRow();
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
        return AuthUser.AUTH_USER.EMAIL;
    }

    @Override
    public Field<String> field4() {
        return AuthUser.AUTH_USER.PASSWORD_HASH;
    }

    @Override
    public Field<String> field5() {
        return AuthUser.AUTH_USER.PASSWORD_RESET_TOKEN;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return AuthUser.AUTH_USER.CREATED_AT;
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
        return getEmail();
    }

    @Override
    public String component4() {
        return getPasswordHash();
    }

    @Override
    public String component5() {
        return getPasswordResetToken();
    }

    @Override
    public LocalDateTime component6() {
        return getCreatedAt();
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
        return getEmail();
    }

    @Override
    public String value4() {
        return getPasswordHash();
    }

    @Override
    public String value5() {
        return getPasswordResetToken();
    }

    @Override
    public LocalDateTime value6() {
        return getCreatedAt();
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
        setEmail(value);
        return this;
    }

    @Override
    public AuthUserRecord value4(String value) {
        setPasswordHash(value);
        return this;
    }

    @Override
    public AuthUserRecord value5(String value) {
        setPasswordResetToken(value);
        return this;
    }

    @Override
    public AuthUserRecord value6(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public AuthUserRecord values(UUID value1, String value2, String value3, String value4, String value5, LocalDateTime value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
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
    public AuthUserRecord(UUID id, String username, String email, String passwordHash, String passwordResetToken, LocalDateTime createdAt) {
        super(AuthUser.AUTH_USER);

        setId(id);
        setUsername(username);
        setEmail(email);
        setPasswordHash(passwordHash);
        setPasswordResetToken(passwordResetToken);
        setCreatedAt(createdAt);
    }
}
