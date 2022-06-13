package com.vfa.repository;

import com.vfa.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT t.players.size FROM Team t WHERE t.id = ?1")
    int countPlayers(int id);
//
//    @Query("SELECT t.players FROM Team t WHERE t.id = ?1")

}
