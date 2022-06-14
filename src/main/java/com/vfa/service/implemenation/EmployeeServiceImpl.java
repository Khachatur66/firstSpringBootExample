package com.vfa.service.implemenation;

import com.vfa.dto.request.EmployeeRequestDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;
import com.vfa.repository.EmployeeRepository;
import com.vfa.service.interfaces.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getById(int id) throws NotFoundException {
        return employeeRepository
                .getByEmployeeId(id)
                .orElseThrow(() -> new NotFoundException("could not find employee with current id: " + id));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

   @Override
    public void save(Employee employee) throws DuplicateDataException, BadRequestException {

        String email = employee.getEmail();

        if (employeeRepository.findByEmail(email) != null) {
            throw new DuplicateDataException(email + " email already exist");
        }

        String firstName = employee.getFirstName();

        int count = employeeRepository.countByFirstName(firstName);
        if (count > 2) {
            throw new BadRequestException("the Number of " + firstName + " should not exceed 3");
        }

        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void updateDTO(int id, EmployeeRequestDTO requestDTO) throws NotFoundException {
        Employee employee = this.getById(id);
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setEmail(requestDTO.getEmail());
    }

    @Override
    public void delete(int id) {
        employeeRepository.deleteById(id);
    }

}
