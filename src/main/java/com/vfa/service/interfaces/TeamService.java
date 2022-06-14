package com.vfa.service.interfaces;

import com.vfa.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService extends AbstractService<Team>{

    int countPlayers(int id);

    Page<Team> getByTeamId(String teamOrigin, Pageable pageable);

//    List<Team> getPlayersNameById(int id) throws NotFoundException;
}
