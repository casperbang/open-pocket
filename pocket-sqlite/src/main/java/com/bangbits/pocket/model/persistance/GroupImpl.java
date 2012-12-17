package com.bangbits.pocket.model.persistance;

import com.bangbits.pocket.model.Group;
import com.bangbits.pocket.model.GroupField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "groups")
public class GroupImpl implements Group{

    private static final long serialVersionUID = 1L;
    //private List<Entry> entries;

    //@DatabaseField(canBeNull = false, foreign = true)
    @ForeignCollectionField(eager = false)
    //@DatabaseField(columnName="")
    //private Collection<GroupField> groupFields;
    //private String icon;

    @DatabaseField(id = true)
    private long _id;
    
    @DatabaseField
    private String title;

    public GroupImpl() {
    }

    public GroupImpl(String title, String icon) {
        this.title = title;
    }

    @Override
    public Long getId() {
        return this._id;
    }

    @Override
    public void setId(Long paramLong) {
        this._id = paramLong;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public void setTitle(String paramString) {
        this.title = paramString;
    }
/*
    @Override
    public Collection<GroupField> getGroupFields() {
        return groupFields;
    }

    @Override
    public void setGroupFields(Collection<GroupField> groupFields) {
        this.groupFields = groupFields;
    }
  */  

    @Override
    public String toString() {
        return "GroupImpl{" + "_id=" + _id + ", title=" + title + '}';
    }
    
    

}
