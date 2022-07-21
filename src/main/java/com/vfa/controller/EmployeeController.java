package com.vfa.controller;

import com.vfa.dto.request.EmployeePasswordRequest;
import com.vfa.dto.request.EmployeeRequest;
import com.vfa.dto.response.EmployeeMailRequest;
import com.vfa.dto.request.EmployeePasswordChangingRequest;
import com.vfa.dto.request.EmployeeResponse;
import com.vfa.exception.AccessDeniedException;
import com.vfa.exception.BadRequestException;
import com.vfa.exception.DuplicateDataException;
import com.vfa.exception.NotFoundException;
import com.vfa.model.Employee;
import com.vfa.service.interfaces.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Void> action(@PathVariable("id") int id, @PathVariable("status") boolean status) throws NotFoundException {
        employeeService.action(id, status);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> updateEmployeeByEmailAndVerificationCode(@Valid @RequestBody EmployeeResponse employeeResponse) throws NotFoundException, AccessDeniedException {
        employeeService.updateEmployeeByEmailAndVerificationCode(employeeResponse);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/refreshing")
    public ResponseEntity<Void> refreshVerificationCode(@RequestBody EmployeeMailRequest employeeMailRequest) {
        employeeService.refreshVerificationCode(employeeMailRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/passcode")
    public ResponseEntity<Void> passcodeGenerator(@RequestBody EmployeeMailRequest employeeMailRequest) throws NotFoundException {
        employeeService.passcodeGenerator(employeeMailRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ChangingPassword")
    public ResponseEntity<Void> passwordChanger(@RequestBody EmployeePasswordChangingRequest passwordChangingResponse) throws NotFoundException {
        employeeService.forgetPassword(passwordChangingResponse);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
