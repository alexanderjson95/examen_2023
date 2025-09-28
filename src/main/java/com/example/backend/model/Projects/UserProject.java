package com.example.backend.model.Projects;


import com.example.backend.model.Users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "join_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType requestType; // om admin skickade inbjudan ska bara mottagare kunna acceptera vice versa,
    private boolean isCreator ; // skapare av projekt, används inte nu men bör dokumenteras
    private boolean isAdmin;
    private boolean hasJoined;
    // när medlem blir inbjuden eller bad om inbjudan
    @CreationTimestamp
    private LocalDateTime requestedDate;
}
