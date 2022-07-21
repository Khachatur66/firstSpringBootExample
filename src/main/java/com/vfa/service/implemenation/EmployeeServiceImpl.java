package com.vfa.service.implemenation;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
import com.vfa.dto.response.EmployeeMailRequest;
import com.vfa.dto.request.EmployeePasswordChangingRequest;
import com.vfa.dto.request.EmployeeResponse;
import com.vfa.enums.EmployeeStatus;
import com.vfa.exception.AccessDeniedException;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.helper.EmailHelper;
import com.vfa.model.Employee;
import com.vfa.repository.EmployeeRepository;
import com.vfa.service.interfaces.EmployeeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final long DIFF = 24 * 60 * 60 * 1000;

    private final EmployeeRepository employeeRepository;

    private final EmailHelper emailHelper;

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmailHelper emailHelper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.emailHelper = emailHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee getById(int id) throws NotFoundException {
        return employeeRepository
                .getByEmployeeId(id)
                .orElseThrow(() -> new NotFoundException("could not find employee with current id: " + id));
    }

    @Override
    public EmployeePasswordRequest getDtoById(int id) throws NotFoundException {
        return employeeRepository.getDtoById(id).orElseThrow(() -> new NotFoundException("could not find employeePasswordRequestDto with current id: " + id));
    }

    @Override
    public Map<String, Object> getEmployeeById(int id) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Object[] objects = employeeRepository.getEmployeeById(id).get(0);
        String firstName = objects[0].toString();
        String lastName = objects[1].toString();
        LocalDate created = (LocalDate) objects[2];

        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("created", created);

        return map;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getByEmail(String email) {
        return employeeRepository.getByEmail(email);
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

        String verificationCode = this.createVerificationCode();
        employee.setVerificationCode(verificationCode);
        employee.setTemporaryVerificationCode(System.currentTimeMillis() + DIFF);

        emailHelper.sendSimpleMessage(employee.getEmail(), employee.getFirstName(), verificationCode, true);

        String password = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(password);

        employeeRepository.save(employee);
    }

    private String createVerificationCode() {
        String verificationCode;

        do {
            verificationCode = RandomStringUtils.randomAlphanumeric(8);
        } while (employeeRepository.findByVerificationCode(verificationCode) != null);

        return verificationCode;
    }

    @Transactional
    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void updateEmployee(EmployeeRequest requestDTO) throws NotFoundException {
        Employee employee = this.getById(requestDTO.getId());
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setEmail(requestDTO.getEmail());
    }

    @Transactional
    @Override
    public void updatePassword(EmployeePasswordRequest passwordRequest) {
        String password = passwordEncoder.encode(passwordRequest.getPassword());
        employeeRepository.changePassword(password, passwordRequest.getId());
    }

    @Transactional
    @Override
    public void action(int id, boolean status) throws NotFoundException {

        Employee employee = this.getById(id);

        if (status) {
            employee.setStatus(EmployeeStatus.ACTIVE);
        } else {
            employee.setStatus(EmployeeStatus.DISABLE);
        }
    }

    @Transactional
    @Override
    public void updateEmployeeByEmailAndVerificationCode(EmployeeResponse employeeResponse) throws NotFoundException, AccessDeniedException {

        Employee employee = employeeRepository.findByEmail(employeeResponse.getEmail());

        if (employeeRepository.findByEmailAndVerificationCode(employeeResponse.getEmail(), employeeResponse.getVerificationCode()) != null) {


            long currentTime = System.currentTimeMillis();

            if (currentTime < employee.getTemporaryVerificationCode()) {
                employee.setStatus(EmployeeStatus.ACTIVE);
                employee.setVerificationCode(null);
                employee.setTemporaryVerificationCode(0);
            } else {
                throw new AccessDeniedException("the Temporary Verification Code has been already expired");
            }

        } else {
            throw new NotFoundException(String.format("Could not find Employee with current Email: %s", employeeResponse.getEmail()));
        }
    }

    @Transactional
    @Override
    public void refreshVerificationCode(EmployeeMailRequest employeeMailRequest) {
        Employee employee = employeeRepository.findByEmail(employeeMailRequest.getEmail());

        employee.setVerificationCode(this.createVerificationCode());
        employee.setTemporaryVerificationCode(System.currentTimeMillis() + DIFF);
    }

    @Transactional
    @Override
    public void passcodeGenerator(EmployeeMailRequest employeeMailRequest) throws NotFoundException {
        Employee employee = employeeRepository.findByEmail(employeeMailRequest.getEmail());

        if (employeeRepository.findByEmail(employeeMailRequest.getEmail()) != null) {
            employee.setPasscode(RandomStringUtils.randomAlphanumeric(8));
            employee.setPasscodeValidityTime(System.currentTimeMillis() + DIFF);
        } else {
            throw new NotFoundException("Could not find Employee with current Email");
        }

        emailHelper.sendSimpleMessage(employeeMailRequest.getEmail(), employee.getFirstName(), employee.getPasscode(), true);
    }

    @Transactional
    @Override
    public void forgetPassword(EmployeePasswordChangingRequest passwordChangingResponse) throws NotFoundException {
        Employee employee = employeeRepository.findByEmail(passwordChangingResponse.getEmail());

        if (passwordChangingResponse.getPasscode().equals(employee.getPasscode())) {
            employee.setPassword(passwordEncoder.encode(passwordChangingResponse.getPassword()));
        } else {
            throw new NotFoundException("Could not find Employee, email or passcode is wrong");
        }

    }

    @Transactional
    @Override
    public void delete(int id) {
        employeeRepository.deleteById(id);
    }
}

