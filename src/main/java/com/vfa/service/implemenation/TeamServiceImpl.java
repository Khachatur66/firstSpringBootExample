package com.vfa.service.implemenation;

import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;
import com.vfa.repository.TeamRepository;
import com.vfa.service.interfaces.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void update(Team team) {
        teamRepository.save(team);
    }

    @Override
    public void delete(int id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team getById(int id) throws NotFoundException {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find team with current id: " + id));
    }

    @Override
    public Page<Team> getByTeamId(String teamOrigin, Pageable pageable) {
        return teamRepository.getByTeamId(teamOrigin, pageable);
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public int countPlayers(int id) {
        return teamRepository.countPlayers(id);
    }

 /*   @Override
    public List<Team> getPlayersNameById(int id) throws NotFoundException {
        return teamRepository.
                getByPlayers(id).
                orElseThrow(() -> new NotFoundException("Could not find player with current id " + id));
    }*/
}
