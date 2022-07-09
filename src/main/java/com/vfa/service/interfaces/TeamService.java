package com.vfa.service.interfaces;

import com.vfa.dto.response.TeamResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService extends AbstractService<Team> {

    int countPlayers(int id) throws InterruptedException;

    Page<Team> getByTeamId(String teamOrigin, Pageable pageable) throws InterruptedException;

//    List<Team> getPlayersNameById(int id) throws NotFoundException;

    List<TeamResponse> getCountById();

    Object getTeamById(int id) throws BadRequestException;

    void edit(String name, int id);

    List<Team> getTeams(Pageable pageable);

    void deleteTeam(int id);

    void saveTeam(Team team);

    List<Team> getAllTeams();
}
