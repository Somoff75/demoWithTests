package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create(Employee employee);

    List<Employee> getAll();

    Employee getById(String id);

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
}
