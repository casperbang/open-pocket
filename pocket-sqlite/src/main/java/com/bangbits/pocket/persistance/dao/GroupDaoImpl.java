package com.bangbits.pocket.persistance.dao;

import com.bangbits.pocket.model.persistance.GroupImpl;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

/**
 * JDBC implementation of GroupDao
 */
public class GroupDaoImpl extends BaseDaoImpl<GroupImpl, Long> implements GroupDao{

    public GroupDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, GroupImpl.class);
    }
    
}
