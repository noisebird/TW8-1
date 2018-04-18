package com.example.employee.restfulapi;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void should_find_employee_by_id_method_return_result() throws Exception {
        Page page=employeeRepository.findAll(new PageRequest(0,5));
        String expectedAllIds="12345";
        List<Employee> content=page.getContent();
        String str="";
        for(Employee item : content)
            str+=item.getId();
        assertEquals(expectedAllIds,str);
        assertEquals(content.size(),5);

    }
}
