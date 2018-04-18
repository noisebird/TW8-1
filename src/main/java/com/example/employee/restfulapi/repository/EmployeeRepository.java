package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //查找所有员工
    List<Employee> findAll();
//    根据id查找员工
    Employee findById(Long id);

//    分页查找
    Page findAll(Pageable pageable);

    //     根据性别来查找
    List<Employee> findByGender(String gender);
}
