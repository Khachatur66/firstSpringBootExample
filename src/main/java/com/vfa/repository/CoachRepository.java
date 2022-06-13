package com.vfa.repository;

import com.vfa.model.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {

    Coach findByFirstName(String firstName);

    int countByLastName(String lastName);

    @Query("SELECT c FROM Coach c WHERE c.id = ?1")
    Optional<Coach> getByCoachTeam(int id);

    @Query("SELECT c FROM Coach c WHERE c.coachExperience BETWEEN ?1 AND ?2")
    List<Coach> getByCoachExperience(int from, int to);

    @Query("SELECT c FROM Coach c WHERE c.team.id = ?1")
    Page<Coach> getByTeamId(int id, Pageable pageable);

    @Query("UPDATE Coach c SET c.firstName = ?1, c.lastName = ?2, c.coachExperience = ?3 WHERE c.id = ?4")
    void updateDTO(String firstName, String lastName, int coachExperience, int id);

}
