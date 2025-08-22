package com.example.backend.model.Projects;

import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProjectResponse {
    private Long projectId;
    private Long userId;
    private String role;
    private Boolean isCreator;
    private Boolean isAdmin;
    private Boolean hasJoined;



    public static UserProjectResponse fromUserProject(UserProject userProject) {
       return UserProjectResponse.builder()
               .projectId(userProject.getId())
               .userId(userProject.getId())
               .role(userProject.getRole())
               .isCreator(userProject.getIsCreator())
               .isAdmin(userProject.getIsAdmin())
               .hasJoined(userProject.getHasJoined())
               .build();
    }
}
