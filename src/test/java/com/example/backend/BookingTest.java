package com.example.backend;

import com.example.backend.model.Projects.Project;
import com.example.backend.model.Projects.ProjectRequest;
import com.example.backend.model.Projects.UserProjectRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.ProjectRepository;
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
public class BookingTest {


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
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepo;


    @BeforeEach
    void cleanDB(){
        projectRepo.deleteAll();
    }


    @Test
    void shouldCreateAProject_success() throws NoSuchAlgorithmException {
        Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
        userService.addUser(users);
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
        Assertions.assertTrue(foundProject.isPresent());
    }

    @Test
    void throwsNoSuchElementException() throws NoSuchAlgorithmException {
        Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
        userService.addUser(users);
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
        Users users = new Users("Alexander", "Alexander123", "alexander@hotmail.com");
        userService.addUser(users);
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

