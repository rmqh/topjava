package ru.javawebinar.topjava.dao;

import java.util.Collection;

public interface BaseDao<T> {

    T add(T t);

    Collection<T> getAll();

    T getById(int id);

    T update(T t);

    void delete(int id);
}
