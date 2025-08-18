package com.example.backend.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String projectName;

    @OneToMany(mappedBy = "project")
    private List<UserProjectBookings> members;
}



//    private String projectDescription;

//    private LocalDate startDate;
//    private LocalDate endDate;
//    private boolean visibility;
//    private String createdBy;
//    private String genre;
//    private boolean salary;
//    private double runtime;

