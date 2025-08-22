package com.example.backend.service;

import com.example.backend.model.Projects.*;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    // Hämtar alla användare i projekt, transactional då vi har lazy loading på entity, så vi måste kunna hämta både projects, users, och userprojects utan att db connection stängs.
    @Transactional
    public List<UserProject> getUserProjects(Long userId, Long projectId, UserProjectRequest req){
        // Null check sker i service
        Users user = userService.findUserById(userId);
        return userProjectRepository.findAllProjectsByUser_Id(user.getId());
    }

    // Hämtar enskilt projekt
    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projektet kunde inte hittas."));
    };

    // Hämtar projekt och användare
    @Transactional
    public UserProject findUserAndProject(Long projectId, Long userId) {
        return userProjectRepository.findByUserIdAndProjectId(projectId,userId)
                .orElseThrow(() -> new NoSuchElementException("Användaren i projektet kunde inte hittas."));
    };



    /* CREATE */

    @Transactional
    public void createProject(ProjectRequest req){
        // Skapar projektet
        Project project = new Project();
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        project.setPublic(req.getIsPublic() != null ? req.getIsPublic() : false);
        project.setSalary(req.getSalary());
        project.setType(req.getType());
        // Sparar projektet
        projectRepository.save(project);
        // Lägger nu till skaparen som projektets första användare
        Users user = userService.findUserById(req.getUserId());
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setIsCreator(true);
        userProjectRepository.save(userProject);
    }

    @Transactional
    // Skapar en användare till projekt
    public void addUserToProject(Long projectId, Long userId, UserProjectRequest req){
        Users user = userService.findUserById(userId);
        Project project = findProjectById(projectId);
        UserProject newUser = new UserProject();
        newUser.setUser(user);
        newUser.setProject(project);
        newUser.setRole(req.getRole());
        newUser.setHasJoined(req.getJoined() != null ? req.getJoined() : false);
        newUser.setIsAdmin(req.getIsAdmin() != null ? req.getIsAdmin() : false);
        userProjectRepository.save(newUser);
    }


    /* PUT */

    @Transactional

    // Uppdaterar behörighet
    public void updateUserProject(Long projectId, Long userId, UserProjectRequest req){
        UserProject userProject = findUserAndProject(userId, projectId);
        userProject.setRole(req.getRole());
        userProject.setHasJoined(req.getJoined() != null ? req.getJoined() : false);
        userProject.setIsAdmin(req.getIsAdmin() != null ? req.getIsAdmin() : false);
        userProjectRepository.save(userProject);
    }
    @Transactional
    public void updateProject(Long projectId, ProjectRequest req){
        Project project = findProjectById(projectId);
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        project.setPublic((req.getIsPublic() != null ? req.getIsPublic() : false));
        project.setSalary(req.getSalary());
        project.setType(req.getType());
        projectRepository.save(project);
    }

    /* DELETE */
    @Transactional
    public void removeUserFromProject(Long userId, Long projectId){
        UserProject userProject = findUserAndProject(userId, projectId);
        userProjectRepository.delete(userProject);
    }

    @Transactional
    public void removeProject(Long id){
        Project project = findProjectById(id);
        userProjectRepository.deleteByProjectId(id);
        projectRepository.delete(project);
    }


}