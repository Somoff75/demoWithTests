package com.example.demowithtests.web.workplace;


import com.example.demowithtests.domain.workplace.Workplace;
import com.example.demowithtests.dto.workplaceDto.WorkplaceRequestDto;
import com.example.demowithtests.dto.workplaceDto.WorkplaceResponseDto;
import com.example.demowithtests.dto.workplaceDto.WorkplaceResponseDtoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface WorkplaceController {

    @Operation(summary = "This is endpoint create new workplace.", description = "Create request to create new workplace", tags = {"Workplace"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CREATED. The workplace was created."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. The database wasn't found."),
            @ApiResponse(responseCode = "500", description = "Unexpectable server error")})
    @PostMapping("/workplace")
    @ResponseStatus(HttpStatus.CREATED)
    WorkplaceResponseDto saveWorkplace(@RequestBody WorkplaceRequestDto requestDto);

    @Operation(summary = "This is endpoint to get a workplace by id.", description = "Create request to get a workplace by id", tags = {"Workplace"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DONE. The workplace was getting."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. The workplace wasn't found."),
            @ApiResponse(responseCode = "500", description = "Unexpectable server error")})
    @GetMapping(value = "/workplace/{id}")
    @ResponseStatus(HttpStatus.OK)
    WorkplaceResponseDto getWorkplaceById(@PathVariable("id") Integer id);


    @Operation(summary = "Update workplace", description = "Update a workplace with new values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated workplace"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "422", description = "Invalid workplace size")})
    @PutMapping(value = "/workplace/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    WorkplaceResponseDto updateWorkplace(@PathVariable("id") Integer id, @RequestBody Workplace newWorkplace);

    @Operation(summary = "Get available workplaces for employees", description = "Returns a list of workplaces that have enough capacity to accommodate the specified number of employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @GetMapping("/workplace/available/{employeeSize}")
    WorkplaceResponseDtoList getAvailableWorkplaces(@PathVariable Integer employeeSize);

}
