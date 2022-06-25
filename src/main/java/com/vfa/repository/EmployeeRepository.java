package com.vfa.repository;

import com.vfa.dto.request.EmployeePasswordRequestDTO;
import com.vfa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);

    int countByFirstName(String firstName);

    @Query("SELECT e FROM Employee e WHERE e.id = ?1")
    Optional<Employee> getByEmployeeId(int id);

    @Query("SELECT e FROM Employee e WHERE e.id = ?1")
    Optional<EmployeePasswordRequestDTO> getDtoById(int id);

    @Query("UPDATE Employee e SET e.firstName = ?1, e.lastName = ?2, e.email = ?3 WHERE e.id = ?4")
    void updateEmployeeDTO(String firstName, String lastName, String email, int id);

    Employee findByVerificationCode(String verificationCode);

    Employee findByPassword(String password);

    @Modifying
    @Query("UPDATE Employee SET password = ?1 WHERE id = ?2")
    void changePassword(String password, int employeeId);
}
