package com.bangbits.pocket.persistance.dao;

import com.bangbits.pocket.model.persistance.FieldImpl;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 * JDBC implementation of FieldDao
 */
public class FieldDaoImpl extends BaseDaoImpl<FieldImpl, Long> implements FieldDao{

    public FieldDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, FieldImpl.class);
    }
    
}
