/*
 * This file is generated by jOOQ.
 */
package ru.ksamorodov.springauth.adapters.db.jooq.tables;


import java.math.BigDecimal;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.ksamorodov.springauth.adapters.db.jooq.Keys;
import ru.ksamorodov.springauth.adapters.db.jooq.Public;
import ru.ksamorodov.springauth.adapters.db.jooq.tables.records.AuthUserRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthUser extends TableImpl<AuthUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.auth_user</code>
     */
    public static final AuthUser AUTH_USER = new AuthUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthUserRecord> getRecordType() {
        return AuthUserRecord.class;
    }

    /**
     * The column <code>public.auth_user.id</code>.
     */
    public final TableField<AuthUserRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.auth_user.username</code>.
     */
    public final TableField<AuthUserRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.auth_user.password_hash</code>.
     */
    public final TableField<AuthUserRecord, String> PASSWORD_HASH = createField(DSL.name("password_hash"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.auth_user.password_reset_token</code>.
     */
    public final TableField<AuthUserRecord, String> PASSWORD_RESET_TOKEN = createField(DSL.name("password_reset_token"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.auth_user.role</code>.
     */
    public final TableField<AuthUserRecord, String> ROLE = createField(DSL.name("role"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>public.auth_user.is_valid_password</code>.
     */
    public final TableField<AuthUserRecord, Boolean> IS_VALID_PASSWORD = createField(DSL.name("is_valid_password"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>public.auth_user.is_temporary_password</code>.
     */
    public final TableField<AuthUserRecord, Boolean> IS_TEMPORARY_PASSWORD = createField(DSL.name("is_temporary_password"), SQLDataType.BOOLEAN.defaultValue(DSL.field("true", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.auth_user.blocked_at</code>.
     */
    public final TableField<AuthUserRecord, Boolean> BLOCKED_AT = createField(DSL.name("blocked_at"), SQLDataType.BOOLEAN.defaultValue(DSL.field("false", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.auth_user.wrong_login_count</code>.
     */
    public final TableField<AuthUserRecord, BigDecimal> WRONG_LOGIN_COUNT = createField(DSL.name("wrong_login_count"), SQLDataType.NUMERIC.defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "");

    private AuthUser(Name alias, Table<AuthUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuthUser(Name alias, Table<AuthUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.auth_user</code> table reference
     */
    public AuthUser(String alias) {
        this(DSL.name(alias), AUTH_USER);
    }

    /**
     * Create an aliased <code>public.auth_user</code> table reference
     */
    public AuthUser(Name alias) {
        this(alias, AUTH_USER);
    }

    /**
     * Create a <code>public.auth_user</code> table reference
     */
    public AuthUser() {
        this(DSL.name("auth_user"), null);
    }

    public <O extends Record> AuthUser(Table<O> child, ForeignKey<O, AuthUserRecord> key) {
        super(child, key, AUTH_USER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<AuthUserRecord> getPrimaryKey() {
        return Keys.AUTH_USER_PKEY;
    }

    @Override
    public AuthUser as(String alias) {
        return new AuthUser(DSL.name(alias), this);
    }

    @Override
    public AuthUser as(Name alias) {
        return new AuthUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthUser rename(String name) {
        return new AuthUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthUser rename(Name name) {
        return new AuthUser(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<UUID, String, String, String, String, Boolean, Boolean, Boolean, BigDecimal> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
