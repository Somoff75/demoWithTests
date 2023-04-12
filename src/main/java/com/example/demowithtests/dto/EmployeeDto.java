package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;


public class EmployeeDto {
    public EmployeeDto(String name, String country, String email, Set<AddressDto> addresses, Set<PhotoDto> photos) {
        this.name = name;
        this.country = country;
        this.email = email;
        this.addresses = addresses;
        this.photos = photos;
    }

        @Schema(description = "Name of an employee.", example = "Buba", required = true)
        public String name;

        @Schema(description = "Name of an employee.", example = "Buba", required = true)
        public String country;

        @Schema(description = "Name of an employee.", example = "Buba", required = true)
        public String email;

        @Schema(description = "Name of an employee.", example = "Buba", required = true)
        public Set<AddressDto> addresses = new HashSet<>();

        @Schema(description = "Name of an employee.", example = "Buba", required = true)
        public Set<PhotoDto> photos = new HashSet<>();
}