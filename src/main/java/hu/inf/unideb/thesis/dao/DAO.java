package hu.inf.unideb.thesis.dao;

import java.util.List;

public interface DAO<T> {

    T findById(long id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void delete(long id);
}
