package com.javarush.radik.repositories;

import java.util.Collection;

public interface Repository <T> {
    T byFindId(long id);
    void create(T obj);
    T update(T obj);
    boolean delete(T obj);
    Collection<T> getAll();

}
