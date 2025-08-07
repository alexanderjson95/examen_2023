package com.projectplatform.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;
    private String projectName;
    private String projectDescription;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean visibility;
    @Column(name="createdBy")
    private String createdBy;
    private String genre;
    private boolean salary;
    private double runtime;
}
