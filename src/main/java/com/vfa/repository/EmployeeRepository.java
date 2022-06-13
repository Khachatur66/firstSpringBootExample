package com.vfa.repository;

import com.vfa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);

    int countByFirstName(String firstName);

    @Query("SELECT e FROM Employee e WHERE e.id = ?1")
    Optional<Employee> getByEmployeeId(int id);
}
