package com.bangbits.pocket.model;

public interface GroupField extends Identifiable<Long>{

    @Override
    Long getId();

    @Override
    void setId(Long id);

    long getGroupId();
    String getTitle();
    boolean isHidden();
    void setGroupId(long paramLong);
    void setHidden(boolean paramBoolean);
    void setTitle(String paramString);
}