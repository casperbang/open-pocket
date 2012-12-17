package com.bangbits.pocket.persistance.dao;

import com.bangbits.pocket.model.persistance.GroupFieldImpl;
import com.bangbits.pocket.model.persistance.GroupImpl;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 * JDBC implementation of GroupDao
 */
public class GroupFieldDaoImpl extends BaseDaoImpl<GroupFieldImpl, Long> implements GroupFieldDao{

    public GroupFieldDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, GroupFieldImpl.class);
    }
    
}
