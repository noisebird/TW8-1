package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    //  更新方法
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Employee e set e.name=?1,e.age=?2,e.gender=?3,e.companyId=?4,e.salary=?5 where e.id=?6")
    int updateEmployee(String name,int age,String gender,Long companyId,int salary,Long id);
}
