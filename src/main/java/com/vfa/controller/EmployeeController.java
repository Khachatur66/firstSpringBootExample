package com.vfa.controller;

import com.vfa.dto.request.EmployeePasswordRequestDTO;
import com.vfa.dto.request.EmployeeRequestDTO;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;
import com.vfa.service.interfaces.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") int id) throws NotFoundException {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Void> getAll() {
        employeeService.getAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Employee employee) throws DuplicateDataException, BadRequestException {
        employeeService.save(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        employeeService.update(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/dto")
    public ResponseEntity<Void> updateDTO(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) throws NotFoundException {
        employeeService.updateEmployee(employeeRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/passwordDto")
    public ResponseEntity<Void> updatePasswordDTO(@Valid @RequestBody EmployeePasswordRequestDTO passwordRequestDTO) throws NotFoundException {
        employeeService.updatePassword(passwordRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
