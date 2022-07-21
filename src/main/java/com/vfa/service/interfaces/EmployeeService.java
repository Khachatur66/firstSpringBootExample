package com.vfa.service.interfaces;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
import com.vfa.dto.response.EmployeeMailRequest;
import com.vfa.dto.request.EmployeePasswordChangingRequest;
import com.vfa.dto.request.EmployeeResponse;
import com.vfa.exception.AccessDeniedException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;

import java.util.Map;
import java.util.Optional;

public interface EmployeeService extends AbstractService<Employee> {

    void updateEmployee(EmployeeRequest requestDTO) throws NotFoundException;

    void updatePassword(EmployeePasswordRequest passwordRequest) throws NotFoundException;

    EmployeePasswordRequest getDtoById(int id) throws NotFoundException;

    Map<String, Object> getEmployeeById(int id);

    Optional<Employee> getByEmail(String email);

    void action(int id, boolean status) throws NotFoundException;

    void updateEmployeeByEmailAndVerificationCode(EmployeeResponse employeeResponse) throws NotFoundException, AccessDeniedException;

    void refreshVerificationCode(EmployeeMailRequest employeeMailRequest);

    void passcodeGenerator(EmployeeMailRequest employeeMailRequest) throws NotFoundException;

    void forgetPassword(EmployeePasswordChangingRequest passwordChangingResponse) throws NotFoundException;

}
