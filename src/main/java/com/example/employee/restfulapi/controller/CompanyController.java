package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
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
@RequestMapping("companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    ResponseEntity<?> getAllCompaniesList() {
        return new ResponseEntity<List<Company>>(companyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Company company = companyRepository.findById(id);
        if (company == null) {
            return new ResponseEntity<String>("id is not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/employees")
    ResponseEntity<?> getEmployeesListByCompanyId(@PathVariable Long id) {
        List<Employee> employees = companyRepository.getEmployeesByCompanyId(id);
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/page/{page}/pageSize/{pageSize}")
    ResponseEntity<?> getEmployeesListByCompanyId(@PathVariable int page, @PathVariable int pageSize) {
        return new ResponseEntity<Page>(companyRepository.findAll(new PageRequest(page, pageSize)), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> addCompany(@RequestBody Company company) {
        Company saveCompany = companyRepository.save(company);
        if ( saveCompany!= null) {
            return new ResponseEntity<Company>(saveCompany, HttpStatus.OK);
        }
        return new ResponseEntity<Company>(new Company(), HttpStatus.EXPECTATION_FAILED);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateCompany(@RequestBody Company company, @PathVariable Long id) {
        if (companyRepository.findById(id) == null) {
            return new ResponseEntity<String>("id is not found!", HttpStatus.NOT_FOUND);
        }
        companyRepository.updateCompany(company.getCompanyName(), company.getEmployeesNumber(), id);
        return new ResponseEntity<Company>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        Company company =companyRepository.findById(id);
        if (company == null) {
            return new ResponseEntity<String>("id is not found!", HttpStatus.NOT_FOUND);
        }
        companyRepository.delete(id);
        if (companyRepository.findById(id) == null) {
            return new ResponseEntity<Company>(company, HttpStatus.OK);
        }
        return  new ResponseEntity<String>("delete company failure", HttpStatus.EXPECTATION_FAILED);

    }

}


