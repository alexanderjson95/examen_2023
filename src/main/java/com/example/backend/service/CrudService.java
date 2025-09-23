package com.example.backend.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
