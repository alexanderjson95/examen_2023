package com.example.backend.model.Projects;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProjectResponse {
    private Long id;
    private String projectName;
    private String description;
    private boolean isPublic;
    private double salary;
    private String type;
    private LocalDateTime created;

    public static ProjectResponse fromProject(Project project) {
        return new ProjectResponse (
                project.getId(),
                project.getProjectName(),
                project.getDescription(),
                project.isPublic(),
                project.getSalary(),
                project.getType(),
                project.getCreated()
        );
    }
}
