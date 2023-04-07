package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.config.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final Service service;
    private final Mapper mapper;

    public Controller(Service service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = mapper.employeeDtoToEmployee(employeeDto);
        return mapper.employeeToEmployeeDto(service.create(employee));
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        List<Employee> employees = service.getAll();
        List<EmployeeReadDto> employeesReadDto = new ArrayList<>();
        for (Employee employee : employees) {
            employeesReadDto.add(
                    mapper.employeeToEmployeeReadDto(employee)
            );
        }
        return employeesReadDto;
    }

    //Получения юзера по id
    @GetMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable String id) {
        return mapper.employeeToEmployeeReadDto(
                service.getById(id)
        );
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployee(@PathVariable("id") String id, @RequestBody EmployeeDto employeeDto) {
        Integer parseId = Integer.parseInt(id);
        return mapper.employeeToEmployeeReadDto(
                service.updateById(parseId, mapper.employeeDtoToEmployee(employeeDto)
                )
        );
    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable String id) {
        Integer parseId = Integer.parseInt(id);
        service.removeById(parseId);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        service.removeAll();
    }

    @PostMapping("/sendEmailByCountry")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailsByCountry(@RequestParam String country, @RequestParam String text) {
        service.sendEmailByCountry(country, text);
    }

    @GetMapping("/replaceNull")
    @ResponseStatus(HttpStatus.OK)
    public void replaceNull() {
        service.processor();
    }

    @PostMapping("/sendEmailByCity")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailsByCity(@RequestParam String cities, @RequestBody String text) {
        service.sendEmailByCity(cities, text);
    }
}