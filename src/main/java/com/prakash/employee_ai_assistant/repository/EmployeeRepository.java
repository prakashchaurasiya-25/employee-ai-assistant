package com.prakash.employee_ai_assistant.repository;

import com.prakash.employee_ai_assistant.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}