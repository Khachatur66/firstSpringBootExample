package com.vfa.service.implemenation;

import com.vfa.dto.request.CoachRequest;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Coach;
import com.vfa.repository.CoachRepository;
import com.vfa.service.interfaces.CoachService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    public Coach getById(@RequestParam int id) throws NotFoundException {
        return coachRepository
                .getByCoachTeam(id)
                .orElseThrow(() -> new NotFoundException("could not find coach with current id: " + id));
    }

    @Override
    public List<Coach> getCoachExperience(int from, int to) {
        return coachRepository.getByCoachExperience(from, to);
    }

    @Override
    public Page<Coach> getByTeamId(int id, Pageable pageable) {
        return coachRepository.getByTeamId(id, pageable);
    }

    @Override
    public List<Coach> getAll() {
        return coachRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Coach coach) throws DuplicateDataException, BadRequestException {

        String firstName = coach.getFirstName();

        if (coachRepository.findByFirstName(firstName) != null) {
            throw new DuplicateDataException(firstName + " firstName already exist");
        }

        String lastName = coach.getLastName();

        int count = coachRepository.countByLastName(lastName);
        if (count > 2) {
            throw new BadRequestException("the Number of " + lastName + " should not exceed 3");
        }
        coachRepository.save(coach);
    }

    @Transactional
    @Override
    public void update(Coach coach) {
        coachRepository.save(coach);
    }

    @Transactional
    @Override
    public void updateDTO(int id, CoachRequest coachRequest) {
        coachRepository.updateDTO(coachRequest.getFirstName(), coachRequest.getLastName(), coachRequest.getCoachExperience(), id);
    }

    /*@Transactional
    @Override
    public void updateTeam(Team team, int id) {
        coachRepository.updateTeam(team, id);
    }*/

    @Transactional
    @Override
    public void delete(int id) {
        coachRepository.deleteById(id);
    }

}
