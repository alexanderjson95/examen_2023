package com.example.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID>{
    protected final JpaRepository<T, ID> repo;

    public AbstractCrudService(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    @Override
    public void save(T entity) {
        repo.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repo.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(ID id) {
          repo.deleteById(id);
    }


}
