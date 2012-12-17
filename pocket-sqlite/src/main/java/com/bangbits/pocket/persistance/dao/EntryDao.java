package com.bangbits.pocket.persistance.dao;

import com.bangbits.pocket.model.persistance.EntryImpl;
import com.j256.ormlite.dao.Dao;

/**
 * Entry DAO with Long as primary key 
 */
public interface EntryDao extends Dao<EntryImpl, Long>{

}
