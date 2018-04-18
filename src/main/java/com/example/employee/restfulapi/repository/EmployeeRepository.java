package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //查找所有员工
    List<Employee> findAll();
}
