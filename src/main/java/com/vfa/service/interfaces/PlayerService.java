package com.vfa.service.interfaces;

import com.vfa.dto.response.PlayerResponseDTO;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService extends AbstractService<Player> {

//    Page<Player> getByTeamId(int id, Pageable pageable);

    List<Player> getByAge(int from, int to);

    PlayerResponseDTO getPlayerInfo(int id) throws NotFoundException;

    void savePlayers(List<Player> playerList);
}
