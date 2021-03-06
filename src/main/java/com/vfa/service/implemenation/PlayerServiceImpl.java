package com.vfa.service.implemenation;

import com.vfa.dto.response.PlayerResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Player;
import com.vfa.repository.PlayerRepository;
import com.vfa.service.interfaces.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player getById(int id) throws NotFoundException {
        return playerRepository
                .getByPlayerId(id)
                .orElseThrow(() -> new NotFoundException("could not find player with current id: " + id));
    }

  /*  @Override
    public Page<Player> getByTeamId(int id, Pageable pageable) {
        return playerRepository.getByTeamId(id, pageable);
    }*/

    @Override
    public List<Player> getByAge(int from, int to) {
        return playerRepository.getByPlayerAge(from, to);
    }

    @Override
    public PlayerResponse getPlayerInfo(int id) {
//      return playerRepository.getPlayerInfo(id);

        List<Object[]> objectList = playerRepository.getPlayerInfo1(id);

        Object[] objects = objectList.get(0);

        String firstName = objects[0].toString();
        String lastName = objects[1].toString();
        int age = (Integer) objects[2];



     /*   Player player = this.getById(id);

        String firstName = player.getFirstName();
        String lastName = player.getLastName();
        int age = player.getAge();*/

        return new PlayerResponse(firstName, lastName, age);
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Player player) throws DuplicateDataException, BadRequestException {

        String firstName = player.getFirstName();
        String lastName = player.getLastName();

        if (playerRepository.findByFirstNameAndLastName(firstName, lastName).isPresent()) {
            throw new DuplicateDataException(firstName + " " + lastName + " already exist");
        }

        playerRepository.save(player);
    }

    @Transactional
    @Override
    public void savePlayers(List<Player> playerList) {
        playerRepository.saveAll(playerList);
    }

    @Transactional
    @Override
    public void update(Player player) {
        playerRepository.save(player);
    }



    @Transactional
    @Override
    public void delete(int id) {
        playerRepository.deleteById(id);
    }


}
