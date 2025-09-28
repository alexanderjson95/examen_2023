package com.example.backend;

import com.example.backend.model.Projects.*;
import com.example.backend.model.Users.Users;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest req, Principal principal){
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);
        System.out.println(username + " " + req.getProjectName());
        projectService.createProject(req, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        List<ProjectResponse> response = projects.stream()
                .map(ProjectResponse::fromProject)
                .toList();
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId, Principal principal){
        Long id = fetchLoggedIn(principal);
        projectService.removeUserFromProject(userId,projectId,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // Behörigheter: admin, joined, blocked
    @PatchMapping("/{projectId}/{userId}")
    public ResponseEntity<UserProjectResponse> updateUserToProject(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId ,
            @RequestBody UserProjectRequest req,
            Principal principal){
        Long id = fetchLoggedIn(principal);
        projectService.updateUserProject(id,userId, projectId, req);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/not-in")
    public ResponseEntity<List<ProjectResponse>> getProjectsUserIsNotIn( Principal principal){
        List<ProjectResponse> projects = projectService.getProjectsUserIsNotIn(fetchLoggedIn(principal));
        return ResponseEntity.ok(projects);
    }



    @GetMapping("/user/projects")
    public ResponseEntity<List<UserProjectResponse>> getAllUsersProjects( Principal principal){
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);

        System.out.println(principal.getName());
        List<UserProjectResponse> projects = projectService.getUserProjects(user.getId());
        return ResponseEntity.ok(projects);    }

    @GetMapping("/{projectId}/users/{userId}")
    public ResponseEntity<UserProjectResponse> getUserAndProjects(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(UserProjectResponse.fromUserProject(projectService.findUserAndProject(userId, projectId)));
    }

    @GetMapping("/{projectId}/users")
    public ResponseEntity<List<UserProjectResponse>> findUsersInProject(@PathVariable("projectId") Long projectId){
        System.out.println(projectId);
        List<UserProjectResponse> users = projectService.findUsersInProject(projectId);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{projectId}/users/{userId}")
    public ResponseEntity<UserProjectResponse> addUserToProject(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId ,
            @RequestBody UserProjectRequest req, Principal p){
        Long id = fetchLoggedIn(p);
        projectService.addUserToProject(id, projectId, userId, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }






    @GetMapping("{projectId}/admin/requested")
    public ResponseEntity<List<UserProjectResponse>> findRequestedToAdmin(@PathVariable Long projectId,Principal principal){
        Long loggedIn = fetchLoggedIn(principal);
        List<UserProjectResponse> projects = projectService.findRequestedToAdmin(loggedIn,projectId);
        return ResponseEntity.ok(projects);
    }

//    @GetMapping("/users/requested")
//    public ResponseEntity<List<UserProjectResponse>> findRequestedToUser(Principal principal){
//        String username = principal.getName();
//        Users user = userService.findUserByUsername(username);
//        System.out.println(principal.getName());
//        List<UserProjectResponse> projects = projectService.findRequestedToUser(user.getId());
//        return ResponseEntity.ok(projects);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponse>> searchUsers(
            @RequestParam String query,
            @RequestParam String value
    ) {
        System.out.println("Kör denna!! SÖKNING! + fick : " + query + " och " + value);
        return ResponseEntity.ok(projectService.findProjectsByQuery(query, value));
    }

    // Helper för att få ut userId av inloggad användare
    private Long fetchLoggedIn(Principal p){
        String username = p.getName();
        return userService.findUserByUsername(username).getId();
    }


    @GetMapping("/{projectId}/requests")
    public ResponseEntity<List<UserProjectResponse>> findRequestedToProject(@PathVariable Long projectId){
        List<UserProjectResponse> projects = projectService.getRequestsForProject(projectId);
        return ResponseEntity.ok(projects);
    }


    @GetMapping("/{projectId}/invites")
    public ResponseEntity<List<UserProjectResponse>> findInvitedToProject(@PathVariable Long projectId){
        List<UserProjectResponse> projects = projectService.getInvitesForProject(projectId);
        return ResponseEntity.ok(projects);
    }


    @GetMapping("/requests")
    public ResponseEntity<List<UserProjectResponse>> findRequestedToUser(Principal principal){
        Long loggedIn = fetchLoggedIn(principal);
        List<UserProjectResponse> projects = projectService.getRequestsForUsers(loggedIn);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<UserProjectResponse>> findAcceptedProjectToUser(Principal principal){
        Long loggedIn = fetchLoggedIn(principal);
        List<UserProjectResponse> projects = projectService.getJoinedForUsers(loggedIn);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/invites")
    public ResponseEntity<List<UserProjectResponse>> findInvitedToUser(Principal principal){
        Long loggedIn = fetchLoggedIn(principal);
        List<UserProjectResponse> projects = projectService.getInvitesForUsers(loggedIn);
        return ResponseEntity.ok(projects);
    }

}


