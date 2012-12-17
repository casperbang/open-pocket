package com.bangbits.pocket.persistance;

import com.bangbits.pocket.PocketException;
import com.bangbits.pocket.model.Group;
import com.bangbits.pocket.model.GroupField;
import com.bangbits.pocket.model.persistance.GroupFieldImpl;
import com.bangbits.pocket.model.persistance.PocketPersistenceService;
import com.bangbits.pocket.persistance.dao.GroupDao;
import com.bangbits.pocket.persistance.dao.GroupDaoImpl;
import com.bangbits.pocket.persistance.dao.GroupFieldDao;
import com.bangbits.pocket.persistance.dao.GroupFieldDaoImpl;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.lang.String;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Pocket business layer sitting on top of the raw DAO layer
 */
public class LocalFilePocketPersistenceService implements PocketPersistenceService{
    
    private String connectionPath;
    
    @Override
    public void setConnectionPath(String path){
        this.connectionPath = path;
    }

    
    @Override
    public Collection<Group> fetchGroups() {
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + connectionPath);
            
            GroupDao dao = new GroupDaoImpl(source);
            //return dao.queryForAll();
            return new ArrayList<Group>( dao.queryForAll() );
            //return new ArrayList<Group>( Arrays.asList( dao.queryForId(17L) ) );
                    
            //Dao<Group, ?> groupDao = DaoManager.createDao(source, Group.class);
            //return groupDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }
    
    @Override
    public Collection<GroupField> fetchGroupFields(Group group){
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + connectionPath);
            
            GroupFieldDao dao = new GroupFieldDaoImpl(source);
            //return dao.queryForAll();
            Map queryMap = new HashMap();
            queryMap.put("groupid", group.getId());
            return new ArrayList<GroupField>( dao.queryForFieldValues( queryMap) );
            //return new ArrayList<Group>( Arrays.asList( dao.queryForId(17L) ) );
                    
            //Dao<Group, ?> groupDao = DaoManager.createDao(source, Group.class);
            //return groupDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }
    
    
}
