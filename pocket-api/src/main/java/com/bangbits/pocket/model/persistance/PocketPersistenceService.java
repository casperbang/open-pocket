package com.bangbits.pocket.model.persistance;

import com.bangbits.pocket.model.Group;
import com.bangbits.pocket.model.GroupField;
import java.util.Collection;
import java.util.List;

public interface PocketPersistenceService {
    
    void setConnectionPath(String path);
            
    Collection<Group> fetchGroups();

    Collection<GroupField> fetchGroupFields(Group group);

}
