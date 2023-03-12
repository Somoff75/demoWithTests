package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.util.DatabaseAccessException;

import java.util.List;

public interface Service {

    Employee create(Employee employee);

    List<Employee> getAll() throws DatabaseAccessException;

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

}
