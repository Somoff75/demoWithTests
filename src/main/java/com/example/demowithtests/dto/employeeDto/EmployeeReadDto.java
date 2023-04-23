package com.example.demowithtests.dto.employeeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;


@ToString
public class EmployeeReadDto {
    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public String name;

    @Schema(description = "Name of an employee.", example = "USA", required = true)
    public String country;

    @Schema(description = "Name of an employee.", example = "buba@gmail.com", required = true)
    public String email;



}

