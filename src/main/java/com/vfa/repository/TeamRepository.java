package com.vfa.repository;

import com.vfa.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT t.players.size FROM Team t WHERE t.id = ?1")
    int countPlayers(int id);

    @Query("SELECT t FROM Team t WHERE t.teamOrigin = ?1")
    Page<Team> getByTeamId(String teamOrigin, Pageable pageable);



}
