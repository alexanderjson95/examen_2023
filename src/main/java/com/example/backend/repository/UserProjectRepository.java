package com.example.backend.repository;

import com.example.backend.model.Projects.UserProject;
import com.example.backend.model.Projects.UserProjectResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Hanterar CRUD för användare i projekt
 */
@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    /** Då vi använder Lazy och detta skapar proxies av relationerna, så hjälper
    **  EntityGraph då den hämtar datan "eagerly". Tanken jag har är att
     *  lazy är default så man måste specificera om man vill ha data eagerly.
     * **/
    @EntityGraph(attributePaths = {"project"})
    List<UserProject> findByUser_Id(Long id);

    @EntityGraph(attributePaths = {"user", "project"})
    Optional<UserProject> findByUserIdAndProjectId(Long userId, Long projectId);

    void deleteByProjectId(Long projectId);

    @EntityGraph(attributePaths = {"user"})
    List<UserProject> findAllByProjectId(Long projectId);

    /**
     *     Till projekt admins:
     *     @return List<UserProject> med aktiva förfrågningar/inbjudningar
     */
    @EntityGraph(attributePaths = {"user"})
    List<UserProject> findByProject_IdAndHasJoinedFalse(Long projectId);

    /**
     *
     * @return List<UserProject> där user inte gått med
     */
    @EntityGraph(attributePaths = {"user"})
    List<UserProject> findByUser_IdAndHasJoinedFalseAndIsAdminFalse(Long projectId);

    boolean existsByProject_IdAndUser_IdAndIsAdminTrue(Long projectId, Long userId);

}
