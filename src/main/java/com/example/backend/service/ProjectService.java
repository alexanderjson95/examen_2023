package com.example.backend.service;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectRequest;
import com.example.backend.model.Projects.UserProject;
import com.example.backend.model.Projects.UserProjectRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ProjectService  {

    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserProjectRepository repo, UserService userService) {
        this.projectRepository = projectRepository;
        this.userProjectRepository = repo;
        this.userService = userService;
    }


    /* READ */

    // Hämtar alla användare i projekt
    public List<UserProject> getUserProjects(UserProjectRequest req){
        // Null check sker i service
        Users user = userService.findUserById(req.getUserId());
        return userProjectRepository.findAllProjectsByUserId(user.getId());
    }

    // Hämtar enskilt projekt
    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projektet kunde inte hittas."));
    };

    // Hämtar projekt och användare
    public UserProject findUserAndProject(Project project, Users user) {
        return userProjectRepository.findByUserAndProject(user,project)
                .orElseThrow(() -> new NoSuchElementException("Användaren i projektet kunde inte hittas."));
    };




    public void createProject(ProjectRequest req){
        // Skapar projektet
        Project project = new Project();
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        project.setPublic(req.isPublic());
        project.setSalary(req.getSalary());
        project.setType(req.getType());
        // Sparar projektet
        projectRepository.save(project);
        // Lägger nu till skaparen som projektets första användare
        Users user = userService.findUserById(req.getUserId());
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setCreator(true);
        userProjectRepository.save(userProject);
    }

    // Skapar en användare till projekt
    public void addUserToProject(UserProjectRequest req){
        Users user = userService.findUserById(req.getUserId());
        Project project = findProjectById(req.getProjectId());
        UserProject newUser = new UserProject();
        newUser.setUser(user);
        newUser.setProject(project);
        newUser.setRole(req.getRole());
        newUser.setJoined(req.isJoined());
        newUser.setAdmin(req.isAdmin());
        newUser.setCreator(req.isCreator());
        userProjectRepository.save(newUser);
    }

    // Uppdaterar behörighet
    public void updateUserProject(UserProjectRequest req){
        Users user = userService.findUserById(req.getUserId());
        Project project = findProjectById(req.getProjectId());
        UserProject userProject = findUserAndProject(project,user);
        userProject.setRole(req.getRole());
        userProject.setJoined(req.isJoined());
        userProject.setAdmin(req.isAdmin());
        userProject.setCreator(req.isCreator());
        userProjectRepository.save(userProject);
    }

    public void removeUserFromProject(UserProjectRequest req){
        Users user = userService.findUserById(req.getUserId());
        Project project = findProjectById(req.getProjectId());
        UserProject userProject = findUserAndProject(project,user);
        userProjectRepository.delete(userProject);
    }


}
