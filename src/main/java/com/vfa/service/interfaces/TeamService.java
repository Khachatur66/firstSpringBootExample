package com.vfa.service.interfaces;

import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;

import java.util.List;

public interface TeamService extends AbstractService<Team>{

    int countPlayers(int id);

//    List<Team> getPlayersNameById(int id) throws NotFoundException;
}
