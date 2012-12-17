package com.bangbits.pocket.model.persistance;

import com.bangbits.pocket.model.GroupField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "groupfields")
public class GroupFieldImpl implements GroupField{

    private long groupId;

    @DatabaseField(id = true, generatedId = true)
    private long _id;
    
    private boolean isHidden;
    
    @DatabaseField()
    private String title;

    public GroupFieldImpl() {
    }

    public GroupFieldImpl(String title, boolean isHidden, long groupId) {
        this.title = title;
        this.isHidden = isHidden;
        this.groupId = groupId;
    }

    public long getGroupId() {
        return this.groupId;
    }

    public Long getId() {
        return this._id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setGroupId(long paramLong) {
        this.groupId = paramLong;
    }

    public void setHidden(boolean paramBoolean) {
        this.isHidden = paramBoolean;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }
}