package com.vfa.repository;

import com.vfa.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);

    int countByFirstName(String firstName);

    @Query("SELECT e FROM Employee e WHERE e.id = ?1")
    Optional<Employee> getByEmployeeEmail(int id);

    @Query("UPDATE Employee e SET e.firstName = ?1, e.lastName = ?2, e.email = ?3 WHERE e.id = ?4")
    void updateEmployeeDTO(String firstName, String lastName, String email, int id);

    Employee findByVerificationCode(String verificationCode);

    @Query("SELECT e FROM Employee e WHERE e.password = ?1  ORDER BY e.id DESC")
    Page<Employee> getByEmployeeEmail(String password, Pageable pageable);
}
