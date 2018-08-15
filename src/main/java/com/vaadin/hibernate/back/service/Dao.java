package com.vaadin.hibernate.back.service;
import java.util.List;

public interface Dao<T> {

    void create(T entity);
    void delete(T entity);
    List<T> getAll();
    List<Kullanicilar> getByEmailPassword(T entity);
    <T> T getById(int id);
    void update(T entity);
    List<T> selectPage(int start, int count);
    Long countAll();
    Integer deleteAll();

}
