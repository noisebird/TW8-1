package com.example.employee.restfulapi;

        import com.example.employee.restfulapi.entity.Employee;
        import com.example.employee.restfulapi.repository.EmployeeRepository;
        import org.flywaydb.core.Flyway;
        import org.junit.Assert;
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
        import static org.junit.Assert.assertFalse;

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
        flyway.setDataSource("jdbc:mysql://192.168.99.100:3306/employee_db","root","root");
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
        Employee employee=employeeRepository.findById(new Integer(1).longValue());
        assertTrue(employee.getName().equals("baidu1"));
    }

    @Test
    public void should_find_employee_by_page_method_return_result() throws Exception {
        Page page=employeeRepository.findAll(new PageRequest(0,5));
        String expectedAllNames="baidu1baidu2baidu3alibaba1alibaba2";
        List<Employee> content=page.getContent();
        String str="";
        for(Employee item : content)
            str+=item.getName();
        assertEquals(expectedAllNames,str);
        assertEquals(content.size(),5);

    }
    @Test
    public void should_find_employee_by_gender_method_return_result() throws Exception {
        List<Employee> employees = employeeRepository.findByGender("male");
        assertEquals(10,employees.size());

    }

    @Test
    public void should_save_employee_will_return_result() throws Exception {
        Employee employee=new Employee("123",12,"male",6000,new Long(2));
        Employee employee1=employeeRepository.save(employee);
        assertFalse(null==employee1);


    }

    @Test
    public void should_update_employee_will_return_result() throws Exception {
        int rows=employeeRepository.updateEmployee("lisi",123,"female",new Long(1),5555,new Long(1));
        Assert.assertTrue(rows==1);
    }

    @Test
    public void should_delete_employee_will_return_result() throws Exception {
        employeeRepository.delete(new Long(1));
        Employee employee=employeeRepository.findById(new Long(1));
        assertEquals(null,employee);
    }
}
