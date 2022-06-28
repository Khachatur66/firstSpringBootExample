package com.vfa.service.interfaces;

import com.vfa.dto.response.TeamResponse;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService extends AbstractService<Team>{

    int countPlayers(int id);

    Page<Team> getByTeamId(String teamOrigin, Pageable pageable);

//    List<Team> getPlayersNameById(int id) throws NotFoundException;

    List<TeamResponse> getCountById();

    Object getTeamById(int id);

    void edit(String name, int id);

    List<Team> getTeams(Pageable pageable);
}
