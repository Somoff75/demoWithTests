package com.example.demowithtests;

import com.example.demowithtests.domain.employee.Employee;
import com.example.demowithtests.dto.employeeDto.EmployeeDto;
import com.example.demowithtests.dto.employeeDto.EmployeeReadDto;
import com.example.demowithtests.service.employee.EmployeeService;
import com.example.demowithtests.util.EmployeesNotFoundException;
import com.example.demowithtests.util.mapstruct.EmployeeMapper;
import com.example.demowithtests.web.employee.EmployeeControllerBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class EmployeeControllerBeanTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeControllerBean employeeControllerBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save an employee")
    void saveEmployeeTest() {
        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();
        when(employeeMapper.employeeDtoToEmployee(employeeDto)).thenReturn(employee);
        when(employeeService.create(employee)).thenReturn(employee);
        employeeControllerBean.saveEmployee(employeeDto);
        verify(employeeMapper).employeeDtoToEmployee(employeeDto);
        verify(employeeService).create(employee);
        verify(employeeMapper).employeeToEmployeeDto(employee);
    }

    @Test
    @DisplayName("Should get all employees")
    void getAllUsersTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        List<EmployeeReadDto> employeeReadDtos = new ArrayList<>();
        employeeReadDtos.add(new EmployeeReadDto());
        employeeReadDtos.add(new EmployeeReadDto());
        when(employeeService.getAll()).thenReturn(employees);
        when(employeeMapper.employeeToEmployeeReadDto(employees.get(0))).thenReturn(employeeReadDtos.get(0));
        when(employeeMapper.employeeToEmployeeReadDto(employees.get(1))).thenReturn(employeeReadDtos.get(1));
        employeeControllerBean.getAllUsers();
        verify(employeeService).getAll();
        verify(employeeMapper, times(2)).employeeToEmployeeReadDto(any(Employee.class));
    }

    @Test
    @DisplayName("Should get an employee by id")
    void getEmployeeByIdTest() {
        Integer id = 1;
        Employee employee = new Employee();
        EmployeeReadDto employeeReadDto = new EmployeeReadDto();
        when(employeeService.getById(id)).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeReadDto(employee)).thenReturn(employeeReadDto);
        employeeControllerBean.getEmployeeById(id);
        verify(employeeService).getById(id);
        verify(employeeMapper).employeeToEmployeeReadDto(employee);
    }
    @Test
    void getEmployeeByIdNotFoundTest() {

        Integer id = 1;
        when(employeeService.getById(id)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> employeeControllerBean.getEmployeeById(id));
        verify(employeeService).getById(id);
    }

    @Test
    void refreshEmployeeTest() throws EmployeesNotFoundException {

        String id = "1";

        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();
        when(employeeMapper.employeeDtoToEmployee(employeeDto)).thenReturn(employee);
        when(employeeService.updateById(anyInt(), any(Employee.class))).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeReadDto(employee)).thenReturn(new EmployeeReadDto());

        EmployeeReadDto result = employeeControllerBean.refreshEmployee(id, employeeDto);

        verify(employeeMapper).employeeDtoToEmployee(employeeDto);
        verify(employeeService).updateById(anyInt(), any(Employee.class));
        verify(employeeMapper).employeeToEmployeeReadDto(employee);
        Assertions.assertNotNull(result);
    }

    @Test
    void removeEmployeeByIdTest() {

        String id = "1";
        employeeControllerBean.removeEmployeeById(id);
        verify(employeeService).removeById(anyInt());
    }

}