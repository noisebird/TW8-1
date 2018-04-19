package com.example.employee.restfulapi.controller;
import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(method = RequestMethod.GET)
    List<Company> getAllCompaniesList() {
        return companyRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Company getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @RequestMapping(value = "/{id}/employees", method = RequestMethod.GET)
    List<Employee> getEmployeesListByCompanyId(@PathVariable Long id) {
        return companyRepository.getEmployeesByCompanyId(id);
    }

    @RequestMapping(value = "/page/{page}/pageSize/{pageSize}", method = RequestMethod.GET)
    Page getEmployeesListByCompanyId(@PathVariable int page, @PathVariable int pageSize) {
        return companyRepository.findAll(new PageRequest(page, pageSize));
    }

    @RequestMapping(method = RequestMethod.POST)
    String addCompany(@RequestBody Company company) {
        if (companyRepository.save(company)!= null) {
            return "添加company成功";
        }
        return "添加company失败";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    String updateCompany(@ModelAttribute Company company, @PathVariable Long id) {
        if (companyRepository.findById(id) == null) {
            throw new Error("The id :" + id + "not exist in table");
        }
        if (companyRepository.updateCompany(company.getCompanyName(), company.getEmployeesNumber(), id) == 1) {
            return "更新company成功！";
        }
        return "更新company失败";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String updateCompany(@PathVariable Long id) {
        if (companyRepository.findById(id) == null) {
            throw new Error("The id :" + id + "not exist in table");
        }
        companyRepository.delete(id);
        if (companyRepository.findById(id) == null) {
            return "删除company成功";
        }
        return "删除company失败";

    }

}


