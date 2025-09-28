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
     * <p> {@link #findRequestedToUser(Long)} </p>
     * <p> {@link #findRequestedToAdmin(Long, Long)} </p>
     * Detta två metoder tar fram förfrågning/inbjudan att gå med i ett projekt.
     *  Så admins kommer kolla efter alla UserProject(joined=false) i project
     *  Users kommer kolla efter alla UserProjects(joined=false) på sig själv

     *  Kan ta findRequestedToUser / findRequestedToAdmin i en metod och flip med _adminExceptionHelper,
     *  men gillar inte att kombinera metoder om det inte ör absolut nödvändigt.
     */

    @Transactional
    public List<UserProjectResponse> findRequestedToUser(Long userId) {
        return userProjectRepository.findByUser_IdAndHasJoinedFalseAndIsAdminFalse(userId)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
    }

    @Transactional()
    public List<UserProjectResponse> findRequestedToAdmin(Long loggedInUserId,Long projectId)  {
        _adminExceptionHelper(loggedInUserId,loggedInUserId,projectId); // sätter att admin targets sig själv här, för vi hämtar ingen särskild target efter id
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
        userProject.setRequestType(RequestType.ACCEPTED);
        userProjectRepository.save(userProject);
    }
    /**
     *
     * @param projectId
     * @param userId
     */
    @Transactional
    public void addUserToProject(Long loggedInUserIdUser, Long projectId, Long userId, UserProjectRequest req){
        _adminExceptionHelper(loggedInUserIdUser,userId,projectId);
        _duplicateExceptionHelper(userId,projectId);

        Users user = userService.findUserById(userId);
        Project project = findProjectById(projectId);

        UserProject newUser = new UserProject();
        newUser.setUser(user);
        newUser.setProject(project);
        newUser.setRequestType(req.getRequestType());

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
    public void updateUserProject(Long loggedInUserId, Long userId, Long projectId, UserProjectRequest req){
        boolean self = loggedInUserId.equals(userId);
        boolean isAdmin = isUserAdmin(projectId, loggedInUserId);
        long adminsLeft = userProjectRepository.countByProject_IdAndIsAdminTrue(projectId);

        if (!isAdmin && !self) {
            throw new NotAdminException("Användaren är inte administratör för projektet");
        }
        // En admin kan ta bort sina egna admin rättigheter, men
        // det får inte tillåtas att ändra sin egna request status
        UserProject userProject = findUserAndProject(userId, projectId);
        if (self && isAdmin && adminsLeft >= 2)
        {
            userProject.setAdmin(req.isAdmin());
        }
        else if (self)
        {
            userProject.setRequestType(req.getRequestType());
        }
        else {
            userProject.setRequestType(req.getRequestType());
            userProject.setAdmin(req.isAdmin());

        }
        userProjectRepository.save(userProject);

    }


    @Transactional
    // Uppdaterar behörighet
    public void handleInvite(Long loggedInUserId, Long userId, Long projectId, UserProjectRequest req){
        boolean self = loggedInUserId.equals(userId);

        if (!isUserAdmin(projectId, loggedInUserId) && !self) {
            throw new NotAdminException("Användaren har inte behörighet för detta för projektet");
        }
        UserProject userProject = findUserAndProject(userId, projectId);
        if (self) {
            userProject.setAdmin(req.isAdmin());
        } else  {
            userProject.setRequestType(req.getRequestType());
            userProjectRepository.save(userProject);
        }
    }




    @Transactional
    public void updateProject(Long loggedInUserId, Long projectId, Long userId, ProjectRequest req){
        _adminExceptionHelper(loggedInUserId,userId,projectId);

        Project project = findProjectById(projectId);
        project.setProjectName(req.getProjectName());
        project.setDescription(req.getDescription());
        project.setGenre(req.getType());
        projectRepository.save(project);
    }

    /* DELETE */
    @Transactional
    public void removeUserFromProject(Long userId, Long projectId, Long loggedInUserId){
        _adminExceptionHelper(loggedInUserId,userId,projectId);
        UserProject userProject = findUserAndProject(userId, projectId);
        userProjectRepository.delete(userProject);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }


    @Transactional
    public void removeProject(Long loggedInUserId,Long projectId){
        _adminExceptionHelper(loggedInUserId,loggedInUserId,projectId);
        Project project = findProjectById(projectId);
        userProjectRepository.deleteByProjectId(projectId);
        projectRepository.delete(project);
    }


    @Transactional
    public List<UserProjectResponse> getInvitesForProject(Long  projectId){
        List<UserProjectResponse> getI =  userProjectRepository.findByProject_IdAndRequestType(projectId,RequestType.INVITE)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        System.out.println("RESPONSEEEEEEEEEEEEEEe" + getI);
        return getI;
    }

    @Transactional
    public List<UserProjectResponse> getRequestsForProject(Long  projectId){
        List<UserProjectResponse> getRe= userProjectRepository.findByProject_IdAndRequestType(projectId,RequestType.REQUEST)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        System.out.println("RESPONSEEEEEEEEEEEEEEe" + getRe);
        return getRe;
    }

    @Transactional
    public List<ProjectResponse> getProjectsUserIsNotIn(Long  userId){
        return projectRepository.findAllProjectsUserIsNotIn(userId)
                .stream()
                .map(ProjectResponse::fromProject)
                .toList();
    }


    @Transactional
    public List<UserProjectResponse> getInvitesForUsers(Long  userId){
        List<UserProjectResponse> get= userProjectRepository.findByUser_IdAndRequestType(userId,RequestType.INVITE)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        System.out.println("RESPONSEEEEEEEEEEEEEEe" + get);
        return get;
    }

    @Transactional
    public List<UserProjectResponse> getRequestsForUsers(Long  userId){
        List<UserProjectResponse> get=  userProjectRepository.findByUser_IdAndRequestType(userId,RequestType.REQUEST)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        System.out.println("RESPONSEEEEEEEEEEEEEEe" + get);
        return get;
    }

    @Transactional
    public List<UserProjectResponse> getJoinedForUsers(Long  userId){
        List<UserProjectResponse> get=  userProjectRepository.findByUser_IdAndRequestType(userId,RequestType.ACCEPTED)
                .stream()
                .map(UserProjectResponse::fromUserProject)
                .toList();
        System.out.println("ACCEPTED" + get);
        return get;
    }


    /**
     * Guards helpers för DRY
     */

    // Förbjuder dubletter i databas
    private void _duplicateExceptionHelper(Long targetId,Long tableId) {
        boolean targetExists = userProjectRepository.findByUserIdAndProjectId(targetId,tableId).isPresent();
        if (targetExists) throw new DataTakenException("Användaren finns redan");
    }


    // Vid fall där användare försöker manipulera annans data
    /*
        [admin, self]
        if 1,0 -> true
        if 0,1 -> false
        if 0,0 -> throw
     */
    private boolean _adminExceptionHelper(Long loggedInUserId, Long userId, Long tableId){
        boolean isAdmin = isUserAdmin(tableId,loggedInUserId);
        boolean targetSelf = loggedInUserId.equals(userId);
        if (!isAdmin && !targetSelf) throw new NotAdminException("Du saknar behörighet för detta");
        return isAdmin;
    }

}