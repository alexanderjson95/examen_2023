package com.example.backend.model.Projects;

import com.example.backend.ToExport;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.model.roles.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToExport

public class UserProjectResponse {
    private Long projectId;
    private Long userId;
    private String projectName;
    private String projectDescription;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private boolean isCreator;
    private boolean isAdmin;
    private boolean hasJoined;
    private boolean isBlocked;
    private LocalDateTime requestedDate;



    public static UserProjectResponse fromUserProject(UserProject userProject) {
       return UserProjectResponse.builder()
               .projectId(userProject.getProject().getId())
               .userId(userProject.getUser().getId())
               .projectName(userProject.getProject().getProjectName())
               .projectDescription(userProject.getProject().getDescription())
               .username(userProject.getUser().getUsername())
               .firstName(userProject.getUser().getFirstName())
               .lastName(userProject.getUser().getLastName())
               .role(userProject.getUser().getUserRoles().stream().map(UserRole::getRole).toString())
               .isCreator(userProject.isCreator())
               .isAdmin(userProject.isAdmin())
               .hasJoined(userProject.isHasJoined())
               .requestedDate(userProject.getRequestedDate())
               .build();
    }
}
