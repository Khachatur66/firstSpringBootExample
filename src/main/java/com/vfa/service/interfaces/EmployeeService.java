package com.vfa.service.interfaces;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
import com.vfa.dto.response.EmployeeResponse;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;

import java.util.Map;
import java.util.Optional;

public interface EmployeeService extends AbstractService<Employee> {

    void updateEmployee(EmployeeRequest requestDTO) throws NotFoundException;

    void updatePassword(EmployeePasswordRequest passwordRequest) throws NotFoundException;

//    EmployeePasswordRequestDTO getDtoById(int id) throws NotFoundException;

    Map<String, Object> getEmployeeById(int id);

    Optional<Employee> getByEmail(String email);

    void action(int id, boolean status);

    void updateEmployeeByEmailAndVerificationCode(EmployeeResponse employeeResponse) throws NotFoundException;

}
