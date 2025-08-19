package com.example.backend.model.Projects;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(max = 15)
    private String projectName;
    @Size(max = 100)
    private String description;
    private boolean isPublic = false;
    private double salary = 0.0;
    private String type;
    @CreationTimestamp
    private LocalDateTime created;
}


