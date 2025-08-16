package com.example.backend.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="projects")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String projectName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;
    public Project(String projectName) {
    }
//    private String projectDescription;

//    private LocalDate startDate;
//    private LocalDate endDate;
//    private boolean visibility;
//    private String createdBy;
//    private String genre;
//    private boolean salary;
//    private double runtime;
}
