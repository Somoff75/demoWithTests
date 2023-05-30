package com.example.demowithtests.dto.employeeDto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public class AddressDto {
    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public Boolean addressHasActive = Boolean.TRUE;

    @Schema(description = "Name of an employee.", example = "Ukraine", required = true)
    public String country;

    @Schema(description = "Name of an employee.", example = "Kyiv", required = true)
    public String city;

    @Schema(description = "Name of an employee.", example = "Sadova", required = true)
    public String street;

    @Schema(description = "Date of adding the employee.", example = "10.10.1999", required = true)
    public Date date = Date.from(Instant.now());

}