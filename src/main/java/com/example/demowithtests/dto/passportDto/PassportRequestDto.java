package com.example.demowithtests.dto.passportDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
public class PassportRequestDto {

    @Schema(description = "First name of an employee in the passport.", example = "Andrew")
    public String firstName;
    @Schema(description = "Second name of an employee in the passport.", example = "Johnson")
    public String secondName;
    @Schema(description = "Birth date of an employee in the passport", example = "10.09.1980")
    public LocalDate birthDate;
    @Schema(description = "Residence of an employee in the passport.", example = "residence")
    public Set<ResidenceDto> registrations = new HashSet<>();
}
