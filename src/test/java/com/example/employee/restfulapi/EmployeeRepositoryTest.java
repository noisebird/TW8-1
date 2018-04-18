package com.example.employee.restfulapi;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by wangjie on 2018/4/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Before
    public void before() {
        Flyway flyway =new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost:3306/employee_db","root","root");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void should_find_all_employee_method_return_result() throws Exception {
        List<Employee> list=employeeRepository.findAll();
        assertTrue(list.size()==15);
    }
}
