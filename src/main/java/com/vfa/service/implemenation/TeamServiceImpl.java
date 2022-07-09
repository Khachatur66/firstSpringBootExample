package com.vfa.service.implemenation;

import com.vfa.dto.response.TeamResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Team;
import com.vfa.repository.TeamRepository;
import com.vfa.service.interfaces.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Page<Team> getByTeamId(String teamOrigin, Pageable pageable) {
        return teamRepository.getByTeamId(teamOrigin, pageable);
    }

    @Override
    public List<TeamResponse> getCountById() {
        return teamRepository.getPlayersCountById();
    }

    @Transactional
    @Override
    public Object getTeamById(int id) throws BadRequestException {
        String select = "SELECT";

        if (id == 1) {
            select = select + " name";
        } else if (id == 2) {
            select = select + " team_origin";
        } else {
            throw new BadRequestException("Cannot get field with current id");
        }

        select = select + " FROM team WHERE id = ?1";

        Query query = entityManager.createNativeQuery(select);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        query.setParameter(1, id);

        return query.getSingleResult();
    }

    @Transactional
    public List<Team> getTeams(Pageable pageable) {
        String[] sort = pageable.getSort().toString().split(":");
        String hql = "SELECT t from Team t order by";
        String s = Arrays.stream(sort).reduce(" ", (s1, s2) -> s1 + s2);
        Query query = entityManager.createQuery(hql + s);
        int size = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        query.setFirstResult(pageNumber * size);
        query.setMaxResults(size);
        return (List<Team>) query.getResultList();
    }

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> getAllTeams() {
        String selectAll = "SELECT t FROM Team t";

        TypedQuery<Team> query = entityManager.createQuery(selectAll, Team.class);

        return query.getResultList();
    }

    @Transactional
    @Override
    public int countPlayers(int id) throws InterruptedException {
        return teamRepository.countPlayers(id);
    }

 /*   @Override
    public List<Team> getPlayersNameById(int id) throws NotFoundException {
        return teamRepository.
                getByPlayers(id).
                orElseThrow(() -> new NotFoundException("Could not find player with current id " + id));
    }*/

    @Override
    public void save(Team team) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("vfa");
        EntityManager entityManager = managerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();
        teamRepository.save(team);
        tx.commit();
    }

    @Transactional
    @Override
    public void saveTeam(Team team) {
        String save = "INSERT INTO team(name, points, playersAmount, teamOrigin) VALUES (?, ?, ?, ?)";

        Query query = entityManager.createNativeQuery(save);

        query.setParameter(1, team.getName());
        query.setParameter(2, team.getPoints());
        query.setParameter(3, team.getPlayersAmount());
        query.setParameter(4, team.getTeamOrigin());

        query.executeUpdate();

    }

    @Transactional
    @Override
    public void update(Team team) {
        teamRepository.save(team);
    }

    @Transactional
    @Override
    public void edit(String name, int id) {
        String update = "UPDATE Team SET name = ?1 WHERE id = ?2";

        Query query = entityManager.createQuery(update);
        query.setParameter(1, name);
        query.setParameter(2, id);
        query.getSingleResult();
    }

    @Transactional
    @Override
    public void delete(int id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteTeam(int id) {
        String delete = "DELETE FROM team WHERE id = ?1";

        Query query = entityManager.createNativeQuery(delete);

        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public Team getById(int id) throws NotFoundException {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find team with current id: " + id));
    }

}
