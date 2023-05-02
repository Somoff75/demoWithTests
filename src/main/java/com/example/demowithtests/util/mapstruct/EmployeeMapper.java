package com.example.demowithtests.util.mapstruct;

import com.example.demowithtests.domain.employee.Employee;
import com.example.demowithtests.dto.employeeDto.EmployeeDto;
import com.example.demowithtests.dto.employeeDto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {



    @Mapping(target = "name", source = "employeeDto.name")
    @Mapping(target = "country", source = "employeeDto.country")
    @Mapping(target = "email", source = "employeeDto.email")
    @Mapping(target = "photos", source = "employeeDto.photos")
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);


    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "country", source = "employee.country")
    @Mapping(target = "email", source = "employee.email")
    @Mapping(target = "photos", source = "employee.photos")
    EmployeeDto employeeToEmployeeDto(Employee employee);

    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "email", source = "employee.email")
    EmployeeReadDto employeeToEmployeeReadDto(Employee employee);

    @Mapping(target = "name", source = "employeeReadDto.name")
    @Mapping(target = "email", source = "employeeReadDto.email")
    Employee employeeReadDtoToEmployee(EmployeeReadDto employeeReadDto);

}