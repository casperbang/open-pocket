package com.bangbits.pocket.model;

import java.util.Collection;
import java.util.List;

public interface Group extends Identifiable<Long>{

    @Override
    Long getId();
    
    @Override
    void setId(Long paramLong);
    
    String getTitle();
    void setTitle(String paramString);
    /*
    Collection<GroupField> getGroupFields();
    
    void setGroupFields(Collection<GroupField> groupFields);
    */

}
