package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("employees")
public class EmployeeController {
    //在此处完成Employee API
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(method = RequestMethod.GET)
    List<Employee> getAllCompaniesList() {
        return employeeRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Employee getCompanyById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @RequestMapping(value = "/page/{page}/pageSize/{pageSize}", method = RequestMethod.GET)
    Page getPageList(@PathVariable int page, @PathVariable int pageSize) {
        return employeeRepository.findAll(new PageRequest(page, pageSize));
    }

    @RequestMapping(value = "/male", method = RequestMethod.GET)
    List<Employee> getGenderList() {
        return employeeRepository.findByGender("male");
    }

    @RequestMapping(method = RequestMethod.POST)
    String addCompany(@RequestBody Employee employee) {
        if (employeeRepository.save(employee) != null) {
            return "添加company成功";
        }
        return "添加数据失败";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
     String updateEmployee(@ModelAttribute Employee employee,@PathVariable Long id) throws Exception {
        if (employeeRepository.findById(id) == null) {
            throw new Exception("Employee not found by id: " + id);
        }
        if (employeeRepository.updateEmployee(employee.getName(), employee.getAge(), employee.getGender(), employee.getCompanyId(), employee.getSalary(), id) == 1) {
            return "数据更新成功";
        }
        return "数据更新失败";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String deleteEmployeeById(@PathVariable Long id) throws Exception {
        if (employeeRepository.findById(id) == null) {
            throw new Exception("Employee do not found by id: " + id);
        }
        employeeRepository.delete(id);
        if (employeeRepository.findById(id) == null) {
            return "删除数据成功";
        }
        return "删除数据失败";

    }

}
