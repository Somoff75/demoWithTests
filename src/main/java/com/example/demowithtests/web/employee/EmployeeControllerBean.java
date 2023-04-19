package com.example.demowithtests.web.employee;


import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.employeeDto.EmployeeDto;
import com.example.demowithtests.dto.employeeDto.EmployeeReadDto;
import com.example.demowithtests.service.employee.EmployeeService;
import com.example.demowithtests.util.EmployeesNotFoundException;
import com.example.demowithtests.util.mapstruct.EmployeeMapper;
import com.example.demowithtests.util.mapstruct.PassportMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final PassportMapper passportMapper;

    @Override
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("Controller --> saveEmployee() - start: ");
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        log.info("Controller --> saveEmployee() is over, employee " + employee + " created");
        return employeeMapper.employeeToEmployeeDto(employeeService.create(employee));
    }

    @Override
    public List<EmployeeReadDto> getAllUsers() {
        log.info("Controller --> getAllUsers() - start: ");
        List<Employee> employees = employeeService.getAll();
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        for (Employee employee : employees) {
            employeesReadDto.add(
                    employeeMapper.employeeToEmployeeReadDto(employee)
            );
        }
        log.info("Controller --> getAllUsers() - is over");
        return employeesReadDto;
    }

    @Override
    public EmployeeReadDto getEmployeeById(@PathVariable String id) {
        log.info("Controller --> getEmployeeById() - start: id={}", id);
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            log.error("Controller --> getEmployeeById() - employee with id {} not found", id);
            throw new EntityNotFoundException("Employee not found with id " + id);
        }
        log.info("Controller --> getEmployeeById() - is over: id={}", id);
        return employeeMapper.employeeToEmployeeReadDto(employee);
    }

    @Override
    public EmployeeReadDto refreshEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) throws EmployeesNotFoundException {
        log.info("Controller --> refreshEmployee() - start: ");
        Integer parseId = Integer.parseInt(id);
        log.info("Controller --> refreshEmployee() - is over: id={}", id);
        return employeeMapper.employeeToEmployeeReadDto(
                employeeService.updateById(parseId, employeeMapper.employeeDtoToEmployee(employeeDto)
                )
        );
    }
    @Override
    public void removeEmployeeById(@PathVariable String id) {
        log.info("Controller --> removeEmployeeById() - start: ");
        Integer parseId = Integer.parseInt(id);
        employeeService.removeById(parseId);
        log.info("Controller --> removeEmployeeById() - is over: id={}", id);
    }

    @Override
    public void removeAllUsers() {
        log.info("Controller --> removeAllUsers() - start: ");
        employeeService.removeAll();
        log.info("Controller --> removeAllUsers() - is over");
    }

    @Override
    public void sendEmailByCountry(@RequestParam String country, @RequestParam String text) {
        log.info("Controller --> sendEmailByCountry() - start: country={}, text={}", country, text);
        employeeService.sendEmailByCountry(country, text);
        log.info("Controller --> sendEmailByCountry() - is over");
    }

    @Override
    public void sendEmailByCity(@RequestParam String cities, @RequestBody String text) {
        log.info("Controller --> sendEmailByCity() - start: cities={}, text={}", cities, text);
        employeeService.sendEmailByCity(cities, text);
        log.info("Controller --> sendEmailByCity() - is over");
    }

    @Override
    public void fillingDatabase(String quantity) {

    }

    @Override
    public void fillDatabase(@PathVariable String quantity) {
        log.info("Controller --> fillDatabase() - start: quantity={}", quantity);
        employeeService.fillDatabase(quantity);
        log.info("Controller --> fillDatabase() - is over");
    }

    @Override
    public void updateByCountry(@RequestParam String countries) {
        log.info("Controller --> updateByCountry() - start: countries={}", countries);
        employeeService.updateByCountry(countries);
        log.info("Controller --> updateByCountry() - is over");
    }

    @Override
    public void cleverUpdateByCountry(@RequestParam String oldCountry, String newCountry) {
        log.info("Controller --> cleverUpdateByCountry() - start: oldCountry={}, newCountry={}", oldCountry, newCountry);
        employeeService.cleverUpdateByCountry(oldCountry, newCountry);
        log.info("Controller --> cleverUpdateByCountry() - is over");
    }

    @Override
    public void sendEmailAllWhoMovedFrom(@RequestParam String country) {
        log.info("Controller --> sendEmailAllWhoMovedFrom() - start: country={}", country);
        employeeService.sendEmailAllWhoMovedFrom(country);
        log.info("Controller --> sendEmailAllWhoMovedFrom() - is over");
    }

    @Override
    public EmployeeReadDto addPassport(Integer employeeId, Integer passportId) {
        Employee employee = employeeService.addPassport(employeeId, passportId);
        EmployeeReadDto employeeReadDto = employeeMapper.employeeToEmployeeReadDto(employee);
        return employeeReadDto;
    }
    public EmployeeReadDto addPassportSafely(Integer employeeId, Integer passportId){
        log.info("Controller ==> addPassportSafely() - start: employeeId = {}, passportId = {}", employeeId, passportId);
        Employee employee = employeeService.addPassport(employeeId, passportId);
        EmployeeReadDto employeeReadDto = employeeMapper.employeeToEmployeeReadDto(employee);
        log.info("Controller ==> addPassportSafely() - end: employee = {}", employeeReadDto);
        return employeeReadDto;
    }



    @Override
    public void replaceNull() {
        log.info("Controller --> replaceNull() - start: ");
        employeeService.processor();
        log.info("Controller --> replaceNull() - is over");
    }


}

