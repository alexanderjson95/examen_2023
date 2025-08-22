package com.example.backend;

import com.example.backend.model.Projects.*;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {


    private final ProjectService projectService;
    private final UserService userService;



    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest req){
        projectService.createProject(req);
        Project createdProject = projectService.findProjectById(req.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProjectResponse.fromProject(projectService.findProjectById(req.getUserId())));
    }




    @DeleteMapping("/{projectId}/users/{userId}")
    public ResponseEntity<?> removeUserFromProject(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // Behörigheter: admin, joined, blocked
    @PatchMapping("/{projectId}/users/{userId}")
    public ResponseEntity<UserProjectResponse> updateUserToProject(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId ,
            @RequestBody UserProjectRequest req){
        projectService.updateUserProject(userId, projectId, req);
        return ResponseEntity.status(HttpStatus.OK).body(UserProjectResponse.fromUserProject(projectService.findUserAndProject(userId, projectId)));
    }


    @GetMapping("/{projectId}/users/{userId}")
    public ResponseEntity<UserProjectResponse> getUserAndProjects(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(UserProjectResponse.fromUserProject(projectService.findUserAndProject(userId, projectId)));
    }
    @PostMapping("/{projectId}/users/{userId}")
    public ResponseEntity<UserProjectResponse> addUserToProject(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId ,
            @RequestBody UserProjectRequest req){
        projectService.addUserToProject(userId, projectId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserProjectResponse.fromUserProject(projectService.findUserAndProject(userId, projectId)));
    }
}


