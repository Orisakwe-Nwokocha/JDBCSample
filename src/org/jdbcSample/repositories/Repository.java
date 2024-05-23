package org.jdbcSample.repositories;

public interface Repository<T> {
    T save(T t);
}