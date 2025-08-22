package com.example.backend;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectRequest;
import com.example.backend.model.Projects.UserProject;
import com.example.backend.model.Projects.UserProjectRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.UserProjectRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ProjectService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProjectTest {


    /*
     * Skapar en sql databas i en container
     */

    @Container
    @ServiceConnection
    static MySQLContainer<?> sql = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.1")
    )
            .withDatabaseName("db")
            .withUsername("u")
            .withPassword("p");


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private UserProjectRepository uProjectRepo;

    String username = "Alexander";
    String password = "Alexander123";
    String email = "alexander123@mail.com";
    String publicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQGr5sZ0R0x2Xv9QeZszv9cG3WgA3dJmCw2xK0lGrg0Y0km6h8AlxV2hlYn3V6ug5pKbmI7GLTfKqkEThj9cK9A==";

    @BeforeEach
    void cleanDB(){
        uProjectRepo.deleteAll();
        projectRepo.deleteAll();
        uRepo.deleteAll();

    }


    @Test
    void shouldReturnUserProjects() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users users = userService.findUserByUsername(username);
        ProjectRequest project = new ProjectRequest();
        project.setUserId(users.getId());
        project.setProjectName("En häftig film");
        project.setType("Långfilm");
        project.setDescription("mycket cool film");
        project.setSalary(0.0);
        projectService.createProject(project);
        Optional<Project> foundProject = projectRepo.findById(project.getUserId());
        Optional<UserProject> foundUserProject = uProjectRepo.findByUserIdAndProjectId(users.getId(), foundProject.get().getId());
        Assertions.assertEquals("En häftig film", foundUserProject.get().getProject().getProjectName());
    }


    @Test
    void shouldCreateAProjectAndAddUser_success() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users users = userService.findUserByUsername(username);
        ProjectRequest project = new ProjectRequest();
        project.setUserId(users.getId());
        project.setProjectName("En häftig film");
        project.setType("Långfilm");
        project.setDescription("mycket cool film");
        project.setSalary(0.0);
        projectService.createProject(project);
        Optional<Project> foundProject = projectRepo.findById(project.getUserId());
        Assertions.assertTrue(foundProject.isPresent());
        Optional<UserProject> foundUserProject = uProjectRepo.findByUserIdAndProjectId(users.getId(), foundProject.get().getId());
        Assertions.assertTrue(foundUserProject.isPresent());
    }

    @Test
    void shouldUpdateProject_success() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users users = userService.findUserByUsername(username);
        ProjectRequest project = new ProjectRequest();
        project.setUserId(users.getId());
        project.setProjectName("En häftig film");
        project.setType("Långfilm");
        project.setDescription("mycket cool film");
        project.setSalary(0.0);
        projectService.createProject(project);
        Optional<Project> oldProject = projectRepo.findById(project.getUserId());
        ProjectRequest updatedProjectReq = new ProjectRequest();
        updatedProjectReq.setUserId(users.getId());
        updatedProjectReq.setProjectName("En inte häftig film");
        updatedProjectReq.setType("Långfilm");
        updatedProjectReq.setDescription("mycket cool film");
        updatedProjectReq.setSalary(0.0);
        projectService.updateProject(oldProject.get().getId(), updatedProjectReq);
        Optional<Project> updatedProject = projectRepo.findById(project.getUserId());
        Assertions.assertNotSame(oldProject,updatedProject);
        Assertions.assertSame(oldProject.get().getId(), updatedProject.get().getId());
    }

    @Test
    void throwsNoSuchElementException() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        ProjectRequest project = new ProjectRequest();
        project.setUserId(10L);
        project.setProjectName("En häftig film");
        project.setType("Långfilm");
        project.setDescription("mycket cool film");
        project.setSalary(0.0);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> projectService.createProject(project));
    }


    @Test
    void shouldRemoveAProject_success() throws NoSuchAlgorithmException {
        userService.addUser(username,password,email,publicKey);
        Users users = userService.findUserByUsername(username);
        ProjectRequest project = new ProjectRequest();
        project.setUserId(users.getId());
        project.setProjectName("En häftig film");
        project.setType("Långfilm");
        project.setDescription("mycket cool film");
        project.setSalary(0.0);
        projectService.createProject(project);

        UserProjectRequest req = new UserProjectRequest();
        req.setUserId(users.getId());
        req.setProjectId(project.getUserId());

        projectService.getUserProjects(req);
        Optional<Project> foundProject = projectRepo.findById(project.getUserId());
        System.out.println("TITLE: " + foundProject.get().getProjectName());
        projectService.removeProject(foundProject.get().getId());
        Optional<Project> deletedProject = projectRepo.findById(project.getUserId());
        Assertions.assertTrue(deletedProject.isEmpty());
    }




}

