package com.vfa.service.implemenation;

import com.vfa.dto.request.RefereeRequest;
import com.vfa.dto.response.RefereeResponse;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Referee;
import com.vfa.repository.RefereeRepository;
import com.vfa.service.interfaces.RefereeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {


    private final RefereeRepository refereeRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    public RefereeResponse getRefereeInfo(int id) {
        return refereeRepository.getRefereeInfo(id);
    }

    @Override
    public Object getRefereeById(int id) throws BadRequestException {
        String select = "SELECT";

        if (id == 1) {
            select = select + " firstName, age, street, building";
        }else if (id == 3) {
            select = select + " firstName, experience, city, country";
        }else {
            throw new BadRequestException("Cannot get Referee with current id");
        }

        select = select + " FROM referee LEFT JOIN address ON address_id = id WHERE id = ?1";

        Query query = entityManager.createNativeQuery(select);
        query.setParameter(1, id);
        return query.getSingleResult();
    }

    @Override
    public List<Referee> getAll() {
        return refereeRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Referee referee) throws DuplicateDataException, BadRequestException {

        String firstName = referee.getFirstName();

        if (refereeRepository.findByFirstName(firstName) != null) {
            throw new DuplicateDataException(firstName + " firstName already exist");
        }

        /*int age1 = refereeRepository.findAge(referee.getAge());

        if (age1 > 60) {
            throw new BadRequestException("the age of referee should not exceed 60");
        }*/

        String lastName = referee.getLastName();

        int count = refereeRepository.countByLastName(lastName);
        if (count > 2) {
            throw new BadRequestException("the Number of " + lastName + " should not exceed 3");
        }
        refereeRepository.save(referee);
    }

    @Transactional
    @Override
    public void update(Referee referee) {
        refereeRepository.save(referee);
    }

    @Transactional
    @Override
    public void updateDTO(int id, RefereeRequest refereeRequest) throws NotFoundException {
        Referee referee = this.getById(id);
        referee.setFirstName(refereeRequest.getFirstName());
        referee.setLastName(refereeRequest.getLastName());
        referee.setRefereeExperience(refereeRequest.getRefereeExperience());
//        refereeRepository.updateByRefereeDto(id, refereeDTO);
    }

    @Transactional
    @Override
    public void delete(int id) {
        refereeRepository.deleteById(id);
    }
}
