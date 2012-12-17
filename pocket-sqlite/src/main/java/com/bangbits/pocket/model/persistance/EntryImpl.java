package com.bangbits.pocket.model.persistance;

import com.bangbits.pocket.model.Entry;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "entries")
public class EntryImpl implements Entry
{
  //private List<Field> fields;
  
  @DatabaseField
  private long groupId;
  
  @DatabaseField(id = true, generatedId = true)
  private long _id;
  
  @DatabaseField
  private String notes;
  
  @DatabaseField
  private String title;

  public EntryImpl()
  {
  }

  public long getGroupId()
  {
    return this.groupId;
  }

  public Long getId()
  {
    return this._id;
  }

  public String getNotes()
  {
    return this.notes;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setGroupId(long paramLong)
  {
    this.groupId = paramLong;
  }

  public void setId(Long paramLong)
  {
    this._id = paramLong;
  }

  public void setNotes(String paramString)
  {
    this.notes = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}
