package com.vfa.service.interfaces;

import com.vfa.dto.request.EmployeeRequestDTO;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;

public interface EmployeeService extends AbstractService<Employee>{

    void updateDTO(EmployeeRequestDTO requestDTO) throws NotFoundException;

//    void saveVerificationCode(Employee employee);
}
