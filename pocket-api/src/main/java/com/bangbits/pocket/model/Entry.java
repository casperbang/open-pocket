package com.bangbits.pocket.model;

public interface Entry extends Identifiable<Long>
{
  @Override
  Long getId();
  @Override
  void setId(Long paramLong);

  long getGroupId();
  String getNotes();
  String getTitle();
  void setGroupId(long paramLong);
  void setNotes(String paramString);
  void setTitle(String paramString);
}
