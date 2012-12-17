package com.bangbits;

/*import com.bangbits.pocket.persistance.dao.GroupDao;
import com.bangbits.pocket.persistance.dao.GroupDaoImpl;
import com.bangbits.pocket.model.persistance.EntryImpl;
import com.bangbits.pocket.model.persistance.FieldImpl;
import com.bangbits.pocket.model.persistance.GroupImpl;
import com.bangbits.pocket.model.persistance.GroupFieldImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;*/
import com.bangbits.pocket.PocketException;
import com.bangbits.pocket.model.Group;
import com.bangbits.pocket.model.GroupField;
import com.bangbits.pocket.model.persistance.PocketPersistenceService;
import com.bangbits.pocket.model.persistance.GroupImpl;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalPocketModel {

    PocketPersistenceService service;
            
    final static String DB_FILENAME = "wallet.db";
    final static String META_FILENAME = "hash.txt";
    
    final String dbPath;
    final String metaPath;
    
    final CryptUtils cryptUtils = new CryptUtils();
    
    final LocalPocketMeta meta;
    final PocketController controller;

    String password = "";
    
    public LocalPocketModel(PocketController controller, String path) {
        
        dbPath = path + DB_FILENAME;
        metaPath = path + META_FILENAME;

        service = getPocketPersistenceService();
        service.setConnectionPath(dbPath);
        
        if (!new File(dbPath).exists())
            throw new PocketException("The required Pocket database file '" + dbPath + "' does not exist!");

        if (!new File(metaPath).exists())
            throw new PocketException("The required Pocket meta-data file '" + metaPath + "' does not exist!");
        
        this.controller = controller;
        this.meta = new LocalPocketMeta(metaPath);
        
        controller.info("Opening Pocket database version " + meta.getVersion() + " of " + DateFormat.getDateTimeInstance().format(meta.getTimestamp()) + "..." );

        if (!meta.isAdvancedEncryption())
            throw new PocketException("This Pocket database is using simple encryption, PocketCLI only supports advanced version!");
        
        this.password = controller.getPassword();
        
        if (!cryptUtils.getHash(password, meta.getHashSalt()).equals(meta.getPasswordHash()))
            throw new PocketException("The specified password does not match database, quitting!");

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            throw new PocketException("org.sqlite.JDBC driver not found!");
        }
        
        controller.info("Groups:");
        {
            for(Group group: service.fetchGroups()){
                controller.info( group.getId() + " - " + decrypt( group.getTitle() ) );
                
                for(GroupField groupField : service.fetchGroupFields(group)){
                    controller.info("+--" + groupField.getTitle());
                }
            }
        }
/*
        controller.info("GroupFields:");
        for(GroupField groupField: fetchGroupFields())
            controller.info( decrypt( groupField.getTitle() ) );

        controller.info("Entries:");
        for(Entry entry : fetchEntrys())
            controller.info( decrypt( entry.getTitle() ) );
        
        controller.info("Fields:");
        for(Field field: fetchFields())
            controller.info( decrypt( field.getTitle() ) );
  */      
        
    }

    /*
    private List<Group> fetchGroups() throws PocketException {
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + dbPath);
            
            GroupDao dao = new GroupDaoImpl(source);
            //return dao.queryForAll();
            return new ArrayList<Group>( Arrays.asList( dao.queryForId(17L) ) );
                    
            //Dao<Group, ?> groupDao = DaoManager.createDao(source, Group.class);
            //return groupDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }*/
    
    /*
    private List<GroupFieldImpl> fetchGroupFields() throws PocketException {
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + dbPath);
            
            Dao<GroupFieldImpl, ?> groupFieldDao = DaoManager.createDao(source, GroupFieldImpl.class);
            
            return groupFieldDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }*/
    
    /*
    private List<EntryImpl> fetchEntrys() throws PocketException {
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + dbPath);
            
            Dao<EntryImpl, ?> entryDao = DaoManager.createDao(source, EntryImpl.class);
            
            return entryDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }*/
    
    /*
    private List<FieldImpl> fetchFields() throws PocketException {
        try {
            JdbcConnectionSource source = new JdbcConnectionSource("jdbc:sqlite:" + dbPath);
            
            Dao<FieldImpl, ?> fieldDao = DaoManager.createDao(source, FieldImpl.class);
            
            return fieldDao.queryForAll();
        } catch (SQLException ex) {
            throw new PocketException(ex.getMessage());
        }
    }
    */
    
    
    
    
    
    
    
    /*
    private List<Group> fetchGroups2() throws PocketException {
        final List<Group> groups = new ArrayList<Group>();
        
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            connection.setReadOnly(true);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);
            ResultSet rs = statement.executeQuery(
                    "   select " +
                    "       groups.title " +
                    "   from " +
                    "       groups "
                    );
            while (rs.next()) {
                groups.add( new GroupImpl( decrypt( rs.getString(1)), "") );
            }   
        } catch (SQLException e) {
            throw new PocketException("Error while accessing database!");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return groups;
    }
    */
    
    private String decrypt(String encrypted){
        return cryptUtils.decrypt(password, meta.getEncryptionSalt(), encrypted);
    }    

    private PocketPersistenceService getPocketPersistenceService(){
    
        final ServiceLoader<PocketPersistenceService> providers = ServiceLoader.load(PocketPersistenceService.class);
    
        if(providers.iterator().hasNext())
            return providers.iterator().next();
    
        throw new PocketException("Unable to locate a DataService");
    }

}

/*
                    "   select " +
                    "       groups.title, entries.title, entries.notes " +
                    "   from " +
                    "       groups, entries " +
                    "   where " +
                    "       groups._id = entries.group_id"
*/