package com.example.demowithtests.service.employee;

import com.example.demowithtests.domain.employee.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {

    @Transactional
    Employee saveWithEntityManager(Employee employee);

    @Transactional
    Employee updateEmployeeWithEntityManager(Integer id, Employee employee);

    @Transactional
    void deleteWithEntityManager(Employee employee);

    Employee findByIdWithEntityManager(Integer id);


    @Transactional
    void detachWithEntityManager(Integer id);

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    void fillDatabase(String quantity);

    void updateByCountry(String country);

    List<Employee> processor();


    List<Employee> sendEmailByCountry(String country, String text);

    List<Employee> sendEmailByCity(String city, String text);


    void cleverUpdateByCountry(String oldCountry, String newCountry);


    Employee createEmployee(String name, String country, String email);

    void sendEmailAllWhoMovedFrom(String country);

    Employee addPassport(Integer employeeId, Integer passportId);


}