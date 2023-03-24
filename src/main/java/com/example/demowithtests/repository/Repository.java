package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@org.springframework.stereotype.Repository
//@Component
public interface Repository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    List<Employee> findAllByIsDeletedNull();

    @Query(value = "select e from Employee e where e.country=:country")
    List<Employee> findAllByCountry(String country);

    @Query(value = "select e from Employee e join e.addresses a where a.city=:city")
    List<Employee> findAllByCity(String city);
}
