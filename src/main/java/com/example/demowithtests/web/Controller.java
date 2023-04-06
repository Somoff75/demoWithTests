package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.Service;
import com.example.demowithtests.util.orica.EmployeeConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@AllArgsConstructor

@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private final Service service;
    private final EmployeeConverter employeeConverter;

    public Controller(Service service, EmployeeConverter employeeConverter) {
        this.service = service;
        this.employeeConverter = employeeConverter;
    }


    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeConverter.toDto(
                service.create(
                        employeeConverter.getMapperFacade().map(employeeDto, Employee.class)
                )
        );
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllUsers() {
        return service.getAll();
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        return employeeConverter.toReadDto(service.getById(id));
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        return employeeConverter.toReadDto(service.updateById(id, employeeConverter.fromDto(employeeDto)));
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
    public void sendEmailByCountry(@RequestParam String country, @RequestParam String text) {
        service.sendEmailByCountry(country, text);
    }

    @GetMapping("/replaceNull")
    @ResponseStatus(HttpStatus.OK)
    public void replaceNull() {
        service.processor();
    }

    @PostMapping("/sendEmailByCity")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmailByCity(@RequestParam String city, @RequestParam String text) {
        service.sendEmailByCity(city, text);
    }

    @PostMapping("/fillDatabase/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public void fillDatabase(@PathVariable String quantity) {
        service.fillDatabase(quantity);

    }
    @PostMapping("/updateByCountry")
    @ResponseStatus(HttpStatus.OK)
    public void updateByCountry(@RequestParam String country) {
        service.updateByCountry(country);
    }
    @PostMapping("/cleverUpdateByCountry")
    @ResponseStatus(HttpStatus.OK)
    public void cleverUpdateByCountry(@RequestParam String oldCountry,@RequestParam String newCountry) {
        service.cleverUpdateByCountry(oldCountry,newCountry);
    }
}
