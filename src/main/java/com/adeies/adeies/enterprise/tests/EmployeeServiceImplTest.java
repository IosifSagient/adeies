package com.adeies.adeies.enterprise.tests;

import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.entities.Department;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.mappers.UpdateEmployeeMapper;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.service.EmployeeService;
import com.adeies.adeies.enterprise.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class EmployeeServiceImplTest {

    @MockBean
    private UpdateEmployeeMapper updateEmployeeMapper;

    @MockBean
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmployeeService employeeService;

    @Before
    public void setUp() {
        employeeRepo.save(getOldEmployee());
    }

    @Test
    public void updateWhenEmployeeExists() {
        EmployeeCard dto = getUpdatedEmp();
        EmployeeRs updatedRs = new EmployeeRs();
        updatedRs.setEmployee(getUpdatedEmp());
        Mockito.when((employeeRepo.findById(anyLong()))).thenReturn(Optional.of(getOldEmployee()));
        Mockito.when(updateEmployeeMapper.toEntity(dto, getOldEmployee())).thenReturn(getUpdatedEmp());

        EmployeeRs employeeRs = employeeService.updateEmployee(dto);

        assertEquals(employeeRs.getEmployee().getFirstName(), updatedRs.getEmployee().getFirstName());
    }

    private EmployeeCard getOldEmployee() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setWording("dev");
        dept.setDescription("sth");
        EmployeeRs employeeRs = new EmployeeRs();
        EmployeeCard oldEmployee = new EmployeeCard();
        oldEmployee.setId(1L);
        oldEmployee.setFirstName("iosif");
        oldEmployee.setLastName("sagient");
        oldEmployee.setArea("Athens");
        oldEmployee.setDepartment(dept);
        oldEmployee.setMaritalStatus("single");
        oldEmployee.setBirthDate(new Date());
        employeeRs.setEmployee(oldEmployee);
        return oldEmployee;
    }

    private EmployeeCard getUpdatedEmp() {
        EmployeeCard updatedEmployee = getOldEmployee();
        updatedEmployee.setFirstName("giannis");
        return updatedEmployee;
    }

    @TestConfiguration
    static class EmployeeImplTestConfiguration {

        //      @Bean
//      public UpdateEmployeeMapper updateEmployeeMapper (){return new UpdateEmployeeMapperImpl();}
        @Bean
        public EmployeeService employeeService(EmployeeRepo employeeRepo, UpdateEmployeeMapper updateEmployeeMapper) {
            return new EmployeeServiceImpl(employeeRepo);
        }
    }

}
