package com.bangbits.pocket.model;

public interface Field extends Identifiable<Long>
{
  @Override
  Long getId();
  
  @Override
  void setId(Long paramLong);

  long getEntryId();
  long getGroupFieldId();
  Long getGroupId();
  String getTitle();
  String getValue();
  boolean isDisplayingPassword();
  boolean isHidden();
  void setDisplayingPassword(boolean paramBoolean);
  void setEntryId(long paramLong);
  void setGroupFieldId(long paramLong);
  void setGroupId(long paramLong);
  void setHidden(boolean paramBoolean);
  void setTitle(String paramString);
  void setValue(String paramString);
}