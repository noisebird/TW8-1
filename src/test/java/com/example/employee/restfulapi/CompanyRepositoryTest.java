package com.example.employee.restfulapi;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.junit.runner.RunWith;
import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wangjie on 2018/4/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Before
    public void before() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://192.168.99.100:3306/employee_db", "root", "root");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void should_find_all_company_method_return_result() throws Exception {
        List<Company> list = companyRepository.findAll();
        assertThat(list.size() == 5).isTrue();
    }

    @Test
    public void should_find_employee_by_id_method_return_result() throws Exception {
        Company company = companyRepository.findById(new Integer(1).longValue());
        assertThat(company.getCompanyName().equals("baidu")).isTrue();
    }

    @Test
    public void should_get_employees_by_companyId_return_result() throws Exception {
        List<Employee> employees = companyRepository.getEmployeesByCompanyId(new Integer(1).longValue());
        assertThat(employees.size() == 3).isTrue();
    }

    @Test
    public void should_find_employee_by_page_method_return_result() throws Exception {
        Page page = companyRepository.findAll(new PageRequest(0, 5));
        String expectedAllNames = "baidualibabaThoughtWorkstengxunhuiwei";
        List<Company> content = page.getContent();
        String str = "";
        for (Company item : content) {
            str += item.getCompanyName();
        }
        assertEquals(expectedAllNames, str);
        assertEquals(content.size(), 5);

    }

    @Test
    public void should_save_employee_will_return_result() throws Exception {
        Company company=new Company("jingdong",4400);
        Company company1=companyRepository.save(company);
        assertFalse(null==company1);


    }

    @Test
    public void should_update_company_will_return_result() throws Exception {
        int rows=companyRepository.updateCompany("abc",6000,new Long(1));
        System.out.println(rows);
        assertTrue(rows==1);
    }

    @Test
    public void should_delete_company_will_return_result() throws Exception {
        companyRepository.delete(new Long(1));
        Company company=companyRepository.findById(new Long(1));
        List<Employee> employees=employeeRepository.findAll();
        assertEquals(null,company);
        assertEquals(employees.size(),12);
    }
}
