package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//@AllArgsConstructor
@Slf4j
@org.springframework.stereotype.Service

public class ServiceBean implements Service {

    private final Repository repository;

    public ServiceBean(Repository repository) {

        this.repository = repository;
    }


    @Override
    public Employee create(Employee employee) {
        if (employee.getName().isEmpty()) {
            throw new InvalidEmployeeException();
        }
        Employee createdEmployee = repository.save(employee);
        return createdEmployee;
    }


    @Override
    public List<Employee> getAll() {
        List<Employee> employeesList;
        try {
            employeesList = repository.findAll();
        } catch (Exception e) {
            throw new EmployeesNotFoundException();
        }
        return employeesList;
    }

    @Override
    public Employee getById(String id) {
        Integer empId = Integer.parseInt(id);
        Employee employee = repository.findById(empId).orElseThrow(IdEmployeeNotFoundException::new);

        if (employee.getIsDeleted()) {
            throw new DeletedEmployeeException();
        }

        return employee;
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
        } catch (DatabaseAccessException e) {
            throw new DeletedEmployeeException();

        }
    }

    @Override
    public List<Employee> processor() {
//        log.info("replace null  - start");
        List<Employee> replaceNull = repository.findAllByIsDeletedNull();
//        log.info("replace null after replace: " + replaceNull);
        for (Employee emp : replaceNull) {
            emp.setIsDeleted(Boolean.FALSE);
        }
//        log.info("replaceNull = {} ");
//        log.info("replace null  - end:");
        return repository.saveAll(replaceNull);

    }

    @Override
    public List<Employee> sendEmailByCountry(String country, String text) {
        List<Employee> employees = repository.findAllByCountry();
        senderEmails(extracted(employees), text);
        return employees;
    }

    @Override
    public List<Employee> sendEmailByCity(String city, String text) {
        List<Employee> employees = repository.findAllByCity(city);
        senderEmails(extracted(employees), text);
        return employees;
    }


    private static List<String> extracted(List<Employee> employees) {
        List<String> emails = new ArrayList<>();
        for (Employee emp : employees) {
            emails.add(emp.getEmail());
        }
        return emails;
    }

    @Override
    public void fillDatabase(String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        for (int i = 0; i < quantity; i++) {
            repository.save(createEmployee("name", "country", "email"));
        }
    }

    @Override
    public void updateByCountry(String country) {
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            employee.setCountry(country);
            repository.save(employee);
        }
    }
    @Override
    @Transactional
    public void cleverUpdateByCountry(String oldCountry, String newCountry) {
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees)
            if (employee.getCountry().equals(oldCountry)) {
                employee.setCountry(newCountry);
                repository.save(employee);
            }
    }
    public String randomCountry() {
        List<String> countries = countryGenerator();
        int randomIndex = (int) (Math.random() * countries.size());
        return countries.get(randomIndex);
    }
    public static List<String> countryGenerator() {
        return Arrays.asList("USA", "Ukraine", "Poland", "Canada", "France", "Germany", "UK", "Japan", "Italy", "India");
    }

    @Override
    public Employee createEmployee(String name, String country, String email) {
        return new Employee(name, country, email);
    }


    public void senderEmails(List<String> emails, String text) {

//        log.info("Sending emails to: " + emails);
    }


}