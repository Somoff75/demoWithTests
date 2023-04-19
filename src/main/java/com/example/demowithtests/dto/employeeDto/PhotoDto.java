package com.example.demowithtests.dto.employeeDto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public class PhotoDto {
    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public String linkPhoto;

    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public Integer length;

    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public Integer width;

    @Schema(description = "Name of an employee.", example = "Buba", required = true)
    public Date createdDate = Date.from(Instant.now());
}
