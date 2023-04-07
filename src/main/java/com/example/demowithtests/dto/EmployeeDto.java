package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Address;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class EmployeeDto {
    public String name;
    public String country;
    public String email;
    public Date creationTime = Date.from(Instant.now());

    public Set<PhotoDto> photos = new HashSet<>();
    public Set<Address> addresses = new HashSet<>();
}