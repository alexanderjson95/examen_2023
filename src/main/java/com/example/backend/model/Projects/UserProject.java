package com.example.backend.model.Projects;


import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_project")
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Size(max = 15)
    // Roll som t.ex kamereman, skådespelare etc.
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private boolean isCreator = false;
    private boolean isAdmin = false;

    // när medlem blir inbjuden eller bad om inbjudan
    @CreationTimestamp
    private LocalDateTime requested;
    // när medlem accepterades eller accepterade
    private LocalDateTime joined;
}
