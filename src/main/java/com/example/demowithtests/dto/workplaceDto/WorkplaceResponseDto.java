package com.example.demowithtests.dto.workplaceDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

@ToString

public class WorkplaceResponseDto {

    @Schema(description = "Info of name", example = "")
    public String name;
    @Schema(description = "Info of address", example = "")
    public String address;
    @Schema(description = "Capacity of workplace.", example = "4")
    public Integer capacity;
    @Schema(description = "Occupied ", example = "")
    public Integer occupied;
    @Schema(description = "This parameter tell us is it possible to connect to this workplace.", example = "true", required = true)
    public Boolean isActive = Boolean.TRUE;

}
