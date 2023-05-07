package com.example.demowithtests.repository;


import com.example.demowithtests.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    List<Employee> findAllByIsDeletedNull();

    @Query(value = "select e from Employee e where e.country=:country")
    List<Employee> findAllByCountry(String country);

    @Query(value = "select e from Employee e join e.addresses a where a.city=:city")
    List<Employee> findAllByCity(String city);
    @Query(value = "SELECT * FROM users WHERE id IN (" +
            "SELECT employee_id FROM addresses WHERE country =:country AND address_has_active = false)", nativeQuery = true)
    List<Employee> updateByCountry(String country);

    @Query(value = "SELECT * FROM employees e WHERE e.id IN (" +
            "SELECT a.employee_id FROM addresses a " +
            "WHERE a.country = :country AND a.address_has_active = false " +
            "AND NOT EXISTS (" +
            "SELECT 1 FROM addresses WHERE employee_id = a.employee_id AND country = :country AND address_has_active = true)" +
            ")", nativeQuery = true)
    List<Employee> findAllMovedFromCountry(@Param("country") String country);




}
