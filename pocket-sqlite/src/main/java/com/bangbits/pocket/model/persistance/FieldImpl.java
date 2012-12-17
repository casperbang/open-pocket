package com.bangbits.pocket.model.persistance;

import com.bangbits.pocket.model.Field;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "fields")
public class FieldImpl implements Field
{
  private boolean displayingPassword = false;
  private long entryId;
  private long groupFieldId;
  private long groupId;
  
  @DatabaseField(id = true, generatedId = true)
  private long _id;
  
  private boolean isHidden;
  
  @DatabaseField
  private String title;
  
  @DatabaseField
  private String value;

  public FieldImpl()
  {
  }

  public FieldImpl(String title, boolean isHidden, long groupId, String value)
  {
    this.title = title;
    this.isHidden = isHidden;
    this.groupId = groupId;
    this.value = value;
  }

  public long getEntryId()
  {
    return this.entryId;
  }

  public long getGroupFieldId()
  {
    return this.groupFieldId;
  }

  public Long getGroupId()
  {
    return this.groupId;
  }

  public Long getId()
  {
    return this._id;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getValue()
  {
    return this.value;
  }

  public boolean isDisplayingPassword()
  {
    return this.displayingPassword;
  }

  public boolean isHidden()
  {
    return this.isHidden;
  }

  public void setDisplayingPassword(boolean paramBoolean)
  {
    this.displayingPassword = paramBoolean;
  }

  public void setEntryId(long paramLong)
  {
    this.entryId = paramLong;
  }

  public void setGroupFieldId(long paramLong)
  {
    this.groupFieldId = paramLong;
  }

  public void setGroupId(long paramLong)
  {
    this.groupId = paramLong;
  }

  public void setHidden(boolean paramBoolean)
  {
    this.isHidden = paramBoolean;
  }

  public void setId(Long paramLong)
  {
    this._id = paramLong;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }
}