package com.example.backend.model.Projects;


import com.example.backend.ToExport;
import lombok.*;

import java.time.LocalDateTime;
@ToExport

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String projectName;
    private String description;
    private LocalDateTime created;
    private JoinType requestRule;

    public static ProjectResponse fromProject(Project project) {
        return new ProjectResponse (
                project.getId(),
                project.getProjectName(),
                project.getDescription(),
                project.getCreated(),
                project.getRequestRule()
        );
    }
}
