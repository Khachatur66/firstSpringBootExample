package com.vfa.service.interfaces;

import com.vfa.dto.response.PlayerResponse;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Player;

import java.util.List;

public interface PlayerService extends AbstractService<Player> {

//    Page<Player> getByTeamId(int id, Pageable pageable);

    List<Player> getByAge(int from, int to);

    PlayerResponse getPlayerInfo(int id) throws NotFoundException;

    void savePlayers(List<Player> playerList);
}
