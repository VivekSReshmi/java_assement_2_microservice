package com.vivek.crud_operations.repository;



import com.vivek.crud_operations.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

