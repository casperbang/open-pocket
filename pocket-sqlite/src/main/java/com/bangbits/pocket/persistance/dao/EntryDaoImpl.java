package com.bangbits.pocket.persistance.dao;

import com.bangbits.pocket.model.persistance.EntryImpl;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 * JDBC implementation of EntryDao
 */
public class EntryDaoImpl extends BaseDaoImpl<EntryImpl, Long> implements EntryDao{

    public EntryDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EntryImpl.class);
    }
    
}
