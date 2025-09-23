package com.example.backend.service;

import com.example.backend.Exceptions.DataTakenException;
import com.example.backend.Exceptions.NotAdminException;
import com.example.backend.Exceptions.ResourceNotFoundException;
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
    public List<UserProjectResponse> getUserProjects(Long userId) {
        Users user = userService.findUserById(userId);
        return userProjectRepository.findByUser_Id(user.getId()).stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
    }

    // Hämtar enskilt projekt
    public Project findProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Projektet kunde inte hittas."));
    };
    @Transactional
    public boolean isUserAdmin(Long projectId, Long userId) {
        return userProjectRepository.existsByProject_IdAndUser_IdAndIsAdminTrue(projectId, userId);
    }
    // Hämtar projekt och användare
    @Transactional
    public UserProject findUserAndProject(Long projectId, Long userId) {
        return userProjectRepository.findByUserIdAndProjectId(projectId,userId)
                .orElseThrow(() -> new NoSuchElementException("Användaren i projektet kunde inte hittas."));
    };

    @Transactional
    public List<UserProjectResponse> findUsersInProject(Long projectId) {
        return userProjectRepository.findAllByProjectId(projectId)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
    }

    /**
     *
     * @link findRequestedToUser
     * @link findRequestedToAdmin
     * Detta två metoder tar fram förfrågning/inbjudan att gå med i ett projekt.
     * Alltså där joined = false och admin = false/true.
     */

    // Tillfällig fungerande lösning pga tidsbrist. Eftersom bara admins kan godkänna
    // user requests till projekt, så kollar vi om user är admin eller inte.
    @Transactional
    public List<UserProjectResponse> findRequestedToUser(Long userId) {
        return userProjectRepository.findByUser_IdAndHasJoinedFalseAndIsAdminFalse(userId)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
    }

    @Transactional()
    public List<UserProjectResponse> findRequestedToAdmin(Long loggedIn, Long projectId, Long userId)  {
        _adminExceptionHelper(loggedIn,userId,projectId);
        return userProjectRepository.findByProject_IdAndHasJoinedFalse(projectId)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        }




    /* CREATE */

    @Transactional
    public void createProject(ProjectRequest req,Long userId){
        // Skapar projektet
        Project project = new Project();
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        // Sparar projektet
        projectRepository.save(project);

        // Lägger nu till skaparen som projektets första användare
        Users user = userService.findUserById(userId);
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProject.setCreator(true);
        userProject.setAdmin(true);
        userProject.setHasJoined(true);
        userProjectRepository.save(userProject);
    }


    /**
     *
     * @param projectId
     * @param userId
     */
    @Transactional
    public void addUserToProject(Long loggedInUser, Long projectId, Long userId){


        _adminExceptionHelper(loggedInUser,userId,projectId);
        _duplicateExceptionHelper(userId,projectId);

        Users user = userService.findUserById(userId);
        Project project = findProjectById(projectId);

        UserProject newUser = new UserProject();
        newUser.setUser(user);
        newUser.setProject(project);

        // joined och admin måste sättas i efterhand i en Patch
        newUser.setHasJoined(false);
        newUser.setAdmin(false);
        userProjectRepository.save(newUser);
    }

    public List<ProjectResponse> findProjectsByQuery(String query, String value){
        List<Project> projects;
        switch (query) {
            case "projectName" -> projects = projectRepository.findAllByProjectNameContainingIgnoreCase(value);
            default -> throw new ResourceNotFoundException("Sökordet finns inte!" + query);
        }
        return projects.stream().map(project ->  ProjectResponse.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .build()).toList();
    }




    /* PUT */

    @Transactional
    // Uppdaterar behörighet
    public void updateUserProject(Long projectId, Long userId, Long id, UserProjectRequest req){
        boolean self = id.equals(userId);

        if (!isUserAdmin(projectId, userId)) {
            throw new NotAdminException("Användaren är inte administratör för projektet");
        }
        // En admin kan ta bort sina egna admin rättigheter, men
        // det får inte tillåtas att göra joined false, eftersom vi ska isåfall ta
        // bort userproject istället. Men om det är admin -> user, så ska de kunna
        // godkänna användare
        UserProject userProject = findUserAndProject(userId, projectId);
        if (self) {
            userProject.setAdmin(req.isAdmin());
        } else  {
            userProject.setHasJoined(req.isJoined());
            userProject.setAdmin(req.isAdmin());
            userProjectRepository.save(userProject);
        }
    }
    @Transactional
    public void updateProject(Long loggedIn, Long projectId, Long userId, ProjectRequest req){
        _adminExceptionHelper(loggedIn,userId,projectId);

        Project project = findProjectById(projectId);
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        project.setGenre(req.getType());
        projectRepository.save(project);
    }

    /* DELETE */
    @Transactional
    public void removeUserFromProject(Long userId, Long projectId, Long loggedIn){
        _adminExceptionHelper(loggedIn,userId,projectId);
        UserProject userProject = findUserAndProject(userId, projectId);
        userProjectRepository.delete(userProject);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    @Transactional
    public void removeProject(Long loggedIn, Long userId, Long projectId){
        _adminExceptionHelper(loggedIn,userId,projectId);
        Project project = findProjectById(projectId);
        userProjectRepository.deleteByProjectId(projectId);
        projectRepository.delete(project);
    }


    /**
     * Guards helpers för DRY
     */

    // Förbjuder dubletter i databas
    private void _duplicateExceptionHelper(Long targetId,Long tableId) {
        boolean targetExists = userProjectRepository.findByUserIdAndProjectId(targetId,tableId).isPresent();
        if (targetExists) throw new DataTakenException("Användaren finns redan");
    }

    // Kollar behörighet
    private void _adminExceptionHelper(Long loggedIn, Long userId, Long tableId){
        boolean isAdmin = isUserAdmin(tableId,userId);
        boolean targetSelf = loggedIn.equals(userId);
        if (!isAdmin && !targetSelf) throw new NotAdminException("Du saknar behörighet för detta");
    }

}