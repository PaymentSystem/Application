package com.epam.lab.paymentsystem.repository;

public interface Dao<T> {
    void add(T type);
    T get(T type);
    void update(T type);
    void delete(T type);
}
