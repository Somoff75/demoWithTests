package com.example.demowithtests.dto.passportDto;

import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;
@ToString
public class PassportResponseDto {

    public Integer id;
    public final UUID serialNumber = UUID.randomUUID();
    public String firstName;
    public String secondName;
    public LocalDate birthDate;
}