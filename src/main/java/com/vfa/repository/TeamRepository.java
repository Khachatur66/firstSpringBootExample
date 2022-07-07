package com.vfa.repository;

import com.vfa.dto.response.TeamResponse;
import com.vfa.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t.players.size FROM Team t WHERE t.id = ?1")
    int countPlayers(int id);

    @Query("SELECT t FROM Team t WHERE t.teamOrigin = ?1")
    Page<Team> getByTeamId(String teamOrigin, Pageable pageable);

    @Query("SELECT new com.vfa.dto.response.TeamResponse(t.name, t.players.size) FROM Team t")
    List<TeamResponse> getPlayersCountById();



}
