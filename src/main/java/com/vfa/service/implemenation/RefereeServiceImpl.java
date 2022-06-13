package com.vfa.service.implemenation;

import com.vfa.dto.request.RefereeRequestDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Referee;
import com.vfa.repository.RefereeRepository;
import com.vfa.service.interfaces.RefereeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeServiceImpl(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    @Override
    public Referee getById(int id) throws NotFoundException {
        return refereeRepository.findById(id).orElseThrow(() -> new NotFoundException("could not find referee with current id: " + id));
    }

    @Override
    public List<Referee> getExperience(int from, int to) {
        return refereeRepository.countRefereeExperience(from, to);
    }

    @Override
    public List<Referee> getAll() {
        return refereeRepository.findAll();
    }

    @Override
    public void save(Referee referee) throws DuplicateDataException, BadRequestException {

        String firstName = referee.getFirstName();

        if (refereeRepository.findByFirstName(firstName) != null) {
            throw new DuplicateDataException(firstName + " firstName already exist");
        }

        String lastName = referee.getLastName();

        int count = refereeRepository.countByLastName(lastName);
        if (count > 2) {
            throw new BadRequestException("the Number of " + lastName + " should not exceed 3");
        }
        refereeRepository.save(referee);
    }

    @Override
    public void update(Referee referee) {
        refereeRepository.save(referee);
    }

    @Override
    public void updateDTO(int id, RefereeRequestDTO refereeRequestDTO) throws NotFoundException {
        Referee referee = this.getById(id);
        referee.setFirstName(refereeRequestDTO.getFirstName());
        referee.setLastName(refereeRequestDTO.getLastName());
        referee.setRefereeExperience(refereeRequestDTO.getRefereeExperience());
//        refereeRepository.updateByRefereeDto(id, refereeDTO);
    }

    @Override
    public void delete(int id) {
        refereeRepository.deleteById(id);
    }
}
