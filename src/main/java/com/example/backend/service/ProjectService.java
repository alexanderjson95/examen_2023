package com.projectplatform.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectplatform.backend.model.Project;
import com.projectplatform.backend.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired private ProjectRepository repo;

    public Project create(Project p) { return repo.save(p); }

    public Project read(int id) { return repo.findById(id).orElseThrow(); }

    public Project update(int id, Project p) { return null;};

    public void delete(int id) {  repo.deleteById(id); }

    public List<Project> readAll() { return repo.findAll(); }

}