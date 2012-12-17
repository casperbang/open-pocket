package com.bangbits.pocket.model;

public interface Identifiable<T> {
    public T getId();
    public void setId(T t);
}
