package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeReadDto {
    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public String name;

    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public String country;

    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public String email;
}

