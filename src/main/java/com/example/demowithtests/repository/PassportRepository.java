package com.example.demowithtests.repository;

import com.example.demowithtests.domain.passport.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

    @Query(value = "select * from passports join residence on residence.passport_id=passports.id where residence.country=:country", nativeQuery = true)
    List<Passport> findPassportsWithCountry(String country);
    @Query(value = "select * from passports join residence on residence.passport_id=passports.id where residence.residence_id=:id", nativeQuery = true)
    Passport findPassportByResidenceId(UUID id);
}
