package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/employees")
public class EmployeeController {
    //在此处完成Employee API
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    ResponseEntity<?> getAllCompaniesList() {
        return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            return new ResponseEntity<String>("id is not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
    ResponseEntity<?> getPageList(@PathVariable int page, @PathVariable int pageSize) {
        return new ResponseEntity<Page>(employeeRepository.findAll(new PageRequest(page, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/male")
    ResponseEntity<?> getGenderList() {
        return new ResponseEntity<List<Employee>>(employeeRepository.findByGender("male"), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> addCompany(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        if (savedEmployee == null) {
            return new ResponseEntity<String>("save employee failure!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) throws Exception {
        if (employeeRepository.findById(id) == null) {
            return new ResponseEntity<String>("id is not found", HttpStatus.NOT_FOUND);
        }
        employeeRepository.updateEmployee(employee.getName(), employee.getAge(), employee.getGender(), employee.getCompanyId(), employee.getSalary(), id);
        return new ResponseEntity<Employee>(employeeRepository.findById(id), HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) throws Exception {
        if (employeeRepository.findById(id) == null) {
            throw new Exception("Employee do not found by id: " + id);
        }
        employeeRepository.delete(id);
        if (employeeRepository.findById(id) == null) {
            return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>("delete employee failure", HttpStatus.NO_CONTENT);

    }

}
