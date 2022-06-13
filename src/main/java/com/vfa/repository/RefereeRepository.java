package com.vfa.repository;

import com.vfa.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

    Referee findByFirstName(String firstName);

    int countByLastName(String lastName);

    @Query("SELECT r FROM Referee r WHERE r.refereeExperience BETWEEN ?1 AND ?2  ORDER BY r.refereeExperience DESC")
    List<Referee> countRefereeExperience(int from, int to);

    @Query("UPDATE Referee r SET r.firstName = ?1, r.lastName = ?2, r.refereeExperience = ?3 WHERE r.id = ?4")
    void updateByRefereeDto(String firstName, String lastName, int refereeExperience, int id);
}
