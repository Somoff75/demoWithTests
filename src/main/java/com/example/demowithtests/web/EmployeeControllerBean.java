package com.example.demowithtests.web;


import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.EmployeesNotFoundException;
import com.example.demowithtests.util.config.Mapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeController {

    private final EmployeeService employeeService;
    private final Mapper mapper;

    @Override
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.employeeDtoToEmployee(employeeDto);
        return mapper.employeeToEmployeeDto(employeeService.create(employee));
    }

    @Override
    public List<EmployeeReadDto> getAllUsers() {
        List<Employee> employees = employeeService.getAll();
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        for (Employee employee : employees) {
            employeesReadDto.add(
                    mapper.employeeToEmployeeReadDto(employee)
            );
        }
        return employeesReadDto;
    }

    @Override
    public EmployeeReadDto getEmployeeById(@PathVariable String id) {
        return mapper.employeeToEmployeeReadDto(
                employeeService.getById(id)
        );
    }

    @Override
    public EmployeeReadDto refreshEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) throws EmployeesNotFoundException {
        Integer parseId = Integer.parseInt(id);
        return mapper.employeeToEmployeeReadDto(
                employeeService.updateById(parseId, mapper.employeeDtoToEmployee(employeeDto)
                )
        );
    }

    @Override
    public void removeEmployeeById(@PathVariable String id) {
        Integer parseId = Integer.parseInt(id);
        employeeService.removeById(parseId);
    }

    @Override
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @Override
    public void sendEmailByCountry(@RequestParam String country, @RequestParam String text) {
        employeeService.sendEmailByCountry(country, text);
    }

    @Override
    public void sendEmailByCity(@RequestParam String cities, @RequestBody String text) {
        employeeService.sendEmailByCity(cities, text);
    }

    @Override
    public void fillingDatabase(String quantity) {

    }

    @Override
    public void fillDatabase(@PathVariable String quantity) {
        employeeService.fillDatabase(quantity);
    }

    @Override
    public void updateByCountry(@RequestParam String countries) {
        employeeService.updateByCountry(countries);
    }

    @Override
    public void cleverUpdateByCountry(@RequestParam String oldCountry, String newCountry) {
        employeeService.cleverUpdateByCountry(oldCountry, newCountry);
    }

    @Override
    public void sendEmailAllWhoMovedFrom(@RequestParam String country) {
        employeeService.sendEmailAllWhoMovedFrom(country);
    }

    @Override
    public void replaceNull() {
        employeeService.processor();
    }


}

