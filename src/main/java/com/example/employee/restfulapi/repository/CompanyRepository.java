package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAll();

    Company findById(long id);

    @Query(value = "select e from Employee e where e.companyId=?1")
    List<Employee> getEmployeesByCompanyId(long companyId);

    Page findAll(Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "update Company c set c.companyName=?1,c.employeesNumber=?2 where c.id=?3")
    int updateCompany(String companyName, int employeesNumber, Long id);


}
