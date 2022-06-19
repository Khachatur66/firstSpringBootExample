package com.vfa.service.interfaces;

import com.vfa.dto.request.EmployeeRequestDTO;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService extends AbstractService<Employee>{

    void updateEmployee(EmployeeRequestDTO requestDTO) throws NotFoundException;

    Page<Employee> getByEmployeeEmail(String password, Pageable pageable);

}
