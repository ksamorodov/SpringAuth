/*
 * This file is generated by jOOQ.
 */
package ru.ksamorodov.springauth.adapters.db.jooq;


import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import ru.ksamorodov.springauth.adapters.db.jooq.tables.AuthUser;
import ru.ksamorodov.springauth.adapters.db.jooq.tables.Databasechangelog;
import ru.ksamorodov.springauth.adapters.db.jooq.tables.Databasechangeloglock;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.auth_user</code>.
     */
    public final AuthUser AUTH_USER = AuthUser.AUTH_USER;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public final Databasechangelog DATABASECHANGELOG = Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public final Databasechangeloglock DATABASECHANGELOGLOCK = Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            AuthUser.AUTH_USER,
            Databasechangelog.DATABASECHANGELOG,
            Databasechangeloglock.DATABASECHANGELOGLOCK
        );
    }
}
