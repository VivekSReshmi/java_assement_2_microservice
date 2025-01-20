package com.vivek.crud_operations.service;



import com.vivek.crud_operations.Entity.Employee;
import com.vivek.crud_operations.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setName(employeeDetails.getName());
            employee.setPosition(employeeDetails.getPosition());
            employee.setSalary(employeeDetails.getSalary());
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee partialUpdateEmployee(Long id, Map<String, Object> updates) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        if (value instanceof String) {
                            employee.setName((String) value);
                        }
                        break;
                    case "position":
                        if (value instanceof String) {
                            employee.setPosition((String) value);
                        }
                        break;
                    case "salary":
                        if (value instanceof Number) {
                            employee.setSalary(((Number) value).doubleValue());
                        }
                        break;
                }
            });
            return employeeRepository.save(employee);
        }
        return null;
    }

}
