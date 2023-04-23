package com.example.demowithtests.service.employee;

import com.example.demowithtests.domain.employee.Employee;
import com.example.demowithtests.domain.passport.Passport;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Slf4j

@org.springframework.stereotype.Service

public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository repository;
    private final PassportRepository passportRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Employee saveWithEntityManager(Employee employee) {
        entityManager.persist(employee);
        log.info ("Employee saved with EntityManager: {}", employee);
        return employee;
    }
    @Transactional
    @Override
    public Employee updateEmployeeWithEntityManager(Integer id, Employee employee) {
        Employee existingEmployee = entityManager.find(Employee.class, id);
        if (existingEmployee == null) {
            throw new IdEmployeeNotFoundException();
        }
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setCountry(employee.getCountry());
        Employee updatedEmployee = entityManager.merge(existingEmployee);
        log.info(String.format("Employee with ID {} updated %d", id));
        return updatedEmployee;
    }

    @Transactional
    @Override
    public void deleteWithEntityManager(Employee employee) {
        log.info("Employee deleted with EntityManager: {}", employee);
        entityManager.remove(employee);
    }

    @Transactional
    @Override
    public Employee findByIdWithEntityManager(Integer id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            throw new EntityNotFoundException("Employee with id " + id + " not found");
        }
        return employee;
    }

    @Transactional
    @Override
    public void detachWithEntityManager(Integer id) {
        Employee employeeToDetach = entityManager.find(Employee.class, id);
        entityManager.detach(employeeToDetach);
        log.info ("Employee detached with EntityManager: {}", id);
}
    @Override
    public Employee create(Employee employee) {
        log.debug("Report --> create() - start: employee = {}", employee);
//        if (employee.getName().isEmpty()) {
//            throw new InvalidEmployeeException();
//        }
        Employee createdEmployee;
        createdEmployee = repository.save(employee);
        log.debug("Report --> create() - is over: employee = {}", createdEmployee);
        return createdEmployee;
    }

    @Override
    public List<Employee> getAll() {
        log.debug("Report --> getAll() - start: ");
        List<Employee> employeesList;
        try {
            employeesList = repository.findAll();
        } catch (Exception e) {
            throw new EmployeesNotFoundException();
        }
        log.debug("Report --> getAll() - is over: ");
        return employeesList;
    }

    @Override
    public Employee getById(String id) {
        log.debug("Report --> getById() - start: id = {}", id);
        Integer empId = Integer.parseInt(id);
        Employee employee = repository.findById(empId).orElseThrow(IdEmployeeNotFoundException::new);
        log.debug("Report --> getById() - is over: employee = {}", employee);
        return employee;
    }

    @Override
    public Employee updateById(Integer id, Employee employee) {
        log.debug("Report --> updateById() - start: id = {}, employee = {}", id, employee);
        try {
            return repository.findById(id)
                    .map(entity -> {
                        if (employee.getName() == null || employee.getEmail() == null || employee.getCountry() == null) {
                            throw new InvalidEmployeeException();
                        }
                        entity.setName(employee.getName());
                        entity.setEmail(employee.getEmail());
                        entity.setCountry(employee.getCountry());
                        log.debug("Report --> updateById() - is over: employee = {}", entity);
                        return repository.save(entity);
                    })
                    .orElseThrow(IdEmployeeNotFoundException::new);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void removeById(Integer id) {
        log.debug("Report --> removeById() - start: id = {}", id);
        try {
            Employee employee = repository.findById(id)
                    .orElseThrow(IdEmployeeNotFoundException::new);
            if (employee.getIsDeleted()) {
                throw new DeletedEmployeeException();
            }
            employee.setIsDeleted(true);
            log.debug("Report --> removeById() - is over: deleted employee = {}", employee);
            repository.save(employee);
        } catch (Exception e) {
            throw new DatabaseAccessException();
        }
    }

    @Override
    public void removeAll() {
        log.debug("Report --> removeAll() - start: ");
        try {
            repository.deleteAll();
            log.debug("Report --> removeAll() - is over: ");
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
        return repository.saveAll(replaceNull);

    }

    @Override
    public List<Employee> sendEmailByCountry(String country, String text) {
        log.debug("Report --> sendEmailByCountry() - start: country = {}, text = {}", country, text);
        List<Employee> employees = repository.findAllByCountry(country);
        senderEmails(extracted(employees), text);
        log.debug("Report --> sendEmailByCountry() - is over: employees = {}", employees);
        return employees;
    }

    @Override
    public List<Employee> sendEmailByCity(String city, String text) {
        log.debug("Report --> sendEmailByCity() - start: city = {}, text = {}", city, text);
        List<Employee> employees = repository.findAllByCity(city);
        senderEmails(extracted(employees), text);
        log.debug("Report --> sendEmailByCity() - is over: employees = {}", employees);
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
        log.debug("Report --> fillDatabase() - start:");
        int quantity = Integer.parseInt(quantityString);
        for (int i = 0; i < quantity; i++) {
            repository.save(createEmployee("name", "country", "email"));
        }
        log.debug("Report --> fillingDataBase() is over: " + quantityString + " employees");
    }

    @Override
    public void updateByCountry(String country) {
        log.debug("Report --> updateByCountry() - start:");
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            employee.setCountry(country);
            repository.save(employee);
        }
        log.debug("Report --> updateByCountry() - is over:");
    }

    @Override
    @Transactional
    public void cleverUpdateByCountry(String oldCountry, String newCountry) {
        log.debug("Report --> cleverUpdateByCountry() - start:");
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees)
            if (employee.getCountry().equals(oldCountry)) {
                employee.setCountry(newCountry);
                repository.save(employee);
            }
        log.debug("Report --> cleverUpdateByCountry() - is over:");
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
        log.debug("Report --> createEmployee() - is over:");
        return new Employee(name, country, email);
    }

    @Override
    public void sendEmailAllWhoMovedFrom(String country) {
        log.debug("Report --> sendEmailAllWhoMovedFrom() - start: country = {}", country);
        List<Employee> employeeListFrom = repository.findAllByCountry(country);
        List<Employee> employeesListMovedFrom = employeeListFrom.stream().filter(employee -> employee.getAddresses().stream()
                        .anyMatch(address -> address.getCountry().equals(country)
                                && !address.getAddressHasActive()))
                .collect(Collectors.toList());
        String text = "Putin died like a mad dog! Go back home!";
        senderEmails(extracted(employeesListMovedFrom), text);
        log.debug("Report --> sendEmailByCity() - is over: employeeListFrom = {}", employeeListFrom);
        System.out.println("Sent emails " + employeesListMovedFrom.size());

    }

    @Override
    public Employee addPassport(Integer employeeId, Integer passportId) {
        Employee employee = repository
                .findById(employeeId)
                .orElseThrow(IdEmployeeNotFoundException::new);
        Passport passport = passportRepository
                .findById(passportId)
                .orElseThrow(IdEmployeeNotFoundException::new);
        employee.setPassport(passport);
        log.debug("employeeService addPassport() completed");
        return repository.save(employee);
    }


    public void senderEmails(List<String> emails, String text) {
        log.info("Sending emails to: " + emails);
    }

}