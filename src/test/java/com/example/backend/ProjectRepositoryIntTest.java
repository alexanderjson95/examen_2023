package com.example.backend;
import com.example.backend.model.Project;
import com.example.backend.repository.ProjectRepository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;




@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProjectRepositoryIntTest {
    

    /*
     * Skapar en container (enviorment) 
     * som skapar en riktig sql databas vi kan testa mot
     */
    @Container
    @ServiceConnection
    static MySQLContainer<?> cont = new MySQLContainer<>("mysql:8.1")
        .withDatabaseName("mockdb")
        .withUsername("mockUsername")
        .withPassword("mockPassword");

    @Autowired
    private ProjectRepository projectRep;

    @Test
    void createProject() {

        projectRep.save(new Project(""));

    }
    

}
