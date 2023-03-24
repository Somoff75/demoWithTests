package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.Repository;
import com.example.demowithtests.util.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
//@Slf4j
@org.springframework.stereotype.Service
public class ServiceBean implements Service {

    private final Repository repository;
    private static final Logger log = Logger.getLogger(ServiceBean.class.getName());

    @Override
    public Employee create(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {

            throw new InvalidEmployeeException();
        }
        log.info("create() - end: employee = {}" + employee);

        return repository.save(employee);
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
    public Employee getById(Integer id) {
        var employee = repository.findById(id).orElseThrow(IdEmployeeNotFoundException::new);

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
        log.info("replace null  - start");
        List<Employee> replaceNull = repository.findAllByIsDeletedNull();
        log.info("replace null after replace: " + replaceNull);
        for (Employee emp : replaceNull) {
            emp.setIsDeleted(Boolean.FALSE);
        }
        log.info("replaceNull = {} ");
        log.info("replace null  - end:");
        repository.saveAll(replaceNull);
        return replaceNull;
    }


    @Override
    public List<Employee> sendEmailByCountry(String country, String text) {
        List<Employee> employees = repository.findAllByCountry(country);
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

    public void senderEmails(List<String> emails, String text) {
        log.info("Sending emails to: " + emails);
    }

}
