package com.example.demowithtests.util.mapstruct;

import com.example.demowithtests.domain.workplace.Workplace;
import com.example.demowithtests.dto.workplaceDto.WorkplaceRequestDto;
import com.example.demowithtests.dto.workplaceDto.WorkplaceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {

    Workplace fromWorkplaceRequestDto(WorkplaceRequestDto workplaceRequestDto);
    WorkplaceResponseDto toWorkplaceResponseDto(Workplace workplace);


}