package com.vfa.controller;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
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

    @GetMapping("/map/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") int id)  {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
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
    public ResponseEntity<Void> updateDTO(@Valid @RequestBody EmployeeRequest employeeRequest) throws NotFoundException {
        employeeService.updateEmployee(employeeRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody EmployeePasswordRequest passwordRequest) throws NotFoundException {
        employeeService.updatePassword(passwordRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
