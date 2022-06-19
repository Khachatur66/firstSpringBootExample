package com.vfa.service.implemenation;

import com.vfa.dto.request.EmployeeRequestDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.helper.EmailHelper;
import com.vfa.model.Employee;
import com.vfa.repository.EmployeeRepository;
import com.vfa.service.interfaces.EmployeeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmailHelper emailHelper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmailHelper emailHelper) {
        this.employeeRepository = employeeRepository;
        this.emailHelper = emailHelper;
    }

    @Override
    public Employee getById(int id) throws NotFoundException {
        return employeeRepository
                .getByEmployeeEmail(id)
                .orElseThrow(() -> new NotFoundException("could not find employee with current id: " + id));
    }

    @Override
    public Page<Employee> getByEmployeeEmail(String password, Pageable pageable) {
        return employeeRepository.getByEmployeeEmail(password, pageable);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Transactional
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

        String verificationCode = this.saveVerificationCode();
        employee.setVerificationCode(verificationCode);

        emailHelper.sendSimpleMessage(employee.getEmail(), employee.getFirstName(), verificationCode);

        employeeRepository.save(employee);
    }

    private String saveVerificationCode() {
        String verificationCode;

       do {
           verificationCode = RandomStringUtils.randomAlphanumeric(8);
        }while (employeeRepository.findByVerificationCode(verificationCode) != null);

        return verificationCode;
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void updateEmployee(EmployeeRequestDTO requestDTO) throws NotFoundException {
        Employee employee = this.getById(requestDTO.getId());
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setEmail(requestDTO.getEmail());
    }

    @Transactional
    @Override
    public void delete(int id) {
        employeeRepository.deleteById(id);
    }

}
