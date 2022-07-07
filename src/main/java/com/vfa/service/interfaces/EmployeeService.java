package com.vfa.service.interfaces;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;

import java.util.Map;

public interface EmployeeService extends AbstractService<Employee>{

    void updateEmployee(EmployeeRequest requestDTO) throws NotFoundException;

    void updatePassword(EmployeePasswordRequest passwordRequestDTO) throws NotFoundException;

//    EmployeePasswordRequestDTO getDtoById(int id) throws NotFoundException;

    Map<String, Object> getEmployeeById(int id);

}
