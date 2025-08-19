package com.example.backend.service;

import com.example.backend.model.Projects.UserProject;
import com.example.backend.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService  {

    @Autowired
    UserProjectRepository repo;

    public List<UserProject> getProjectsByUser(long userId){
        return repo.findByUserId(userId);
    }

    public List<UserProject> getProjectsByProject(long projectId){
        return repo.findByProjectId(projectId);
    }

    public UserProject addUserToProject(UserProject userProject){
        return repo.save(userProject);
    }

    public void removeUserFromProject(long userId){
         repo.deleteById(userId);
    }

}
