package com.example.demowithtests;

import com.example.demowithtests.domain.employee.Employee;
import com.example.demowithtests.domain.passport.Passport;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.service.employee.EmployeeServiceBean;
import com.example.demowithtests.util.IdEmployeeNotFoundException;
import com.example.demowithtests.util.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PassportRepository passportRepository;

    @InjectMocks
    private EmployeeServiceBean service;


    @Test
    @DisplayName("Save employee test")
    public void whenSaveEmployee_shouldReturnEmployee() {
        Employee employee = new Employee();
        when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        var created = service.create(employee);
        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Get employee by exist id test")
    public void whenGivenId_shouldReturnEmployee_ifFound() {

        Employee employee = new Employee();
        employee.setId(7);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {

        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

    @Test
    @DisplayName("Read employee by id test")
    public void readEmployeeByIdTest() {
        Employee employee = new Employee();
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        Employee expected = service.getById(employee.getId());
        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("Read all employees test")
    public void readAllEmployeesTest() {
        Employee employee = new Employee();
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        var list = employeeRepository.findAll();
        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Remove employee by id test")
    public void removeEmployeeByIdTest() {
        // Arrange
        Integer id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        service.removeById(id);

        // Assert
        verify(employeeRepository).findById(id);
        verify(employeeRepository).save(employee);
        assertTrue(employee.getIsDeleted());
    }

    @Test
    @DisplayName("Remove non-existing employee by id test")
    public void removeNonExistingEmployeeByIdTest() {
        // Arrange
        Integer id = 1;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IdEmployeeNotFoundException.class, () -> service.removeById(id));

        verify(employeeRepository).findById(id);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Add passport test")
    public void testAddPassport() {
        Passport passport = new Passport();
        passport.setId(1);
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        Mockito.when(passportRepository.findById(1)).thenReturn(Optional.of(passport));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        assertThat(service.addPassport(1, 1).getPassport()).isSameAs(passport);

    }
}