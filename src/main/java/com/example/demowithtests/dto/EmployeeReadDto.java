package com.example.demowithtests.dto;

import java.util.HashSet;
import java.util.Set;

public class EmployeeReadDto {
    public String name;
    public String email;

    public Set<PhotoDto> photos = new HashSet<>();

    public Set<AddressDto> addresses = new HashSet<>();
}
