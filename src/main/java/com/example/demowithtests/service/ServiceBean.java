package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;

    @Override
    public Employee create(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty() ||
                employee.getCountry() == null || employee.getCountry().isEmpty() ||
                employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new InvalidEmployeeException();
        }
        if (repository.existsByName(employee.getName()) || repository.existsByEmail(employee.getEmail())) {
            throw new EmployeeAlreadyExistsException();
        }

    return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() throws DatabaseAccessException {
        try {
            List<Employee> employees = repository.findAll();
            if (employees.isEmpty()) {
                throw new EmployeesNotFoundException();
            }
            return employees;
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }


    @Override
    public Employee getById(Integer id) {
        try {
            Employee employee = repository.findById(id)
                    .orElseThrow(IdEmployeeNotFoundException::new);
            if (employee.getIsDeleted()) {
                throw new DeletedEmployeeException();
            }
            return employee;
//        } catch (Exception e) {
//            throw new DatabaseAccessException();
        } finally {
            log.info("Get employee by id: " + id);
        }
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        try {
            return repository.findById(id)
                    .map(entity -> {
                        if (employee.getName() == null || employee.getEmail() == null || employee.getCountry() == null) {
                            throw new InvalidEmployeeException();
                        }
                        entity.setName(employee.getName());
                        entity.setEmail(employee.getEmail());
                        entity.setCountry(employee.getCountry());
                        return repository.save(entity);
                    })
                    .orElseThrow(IdEmployeeNotFoundException::new);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void removeById(Integer id) {
        try {
            Employee employee = repository.findById(id)
                    .orElseThrow(IdEmployeeNotFoundException::new);
            if (employee.getIsDeleted()) {
                throw new DeletedEmployeeException();
            }
            employee.setIsDeleted(true);
            repository.save(employee);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void removeAll() {
        try {
            repository.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while removing all employees", e);
        }

    }
}
