package com.example.demowithtests.web.workplace;

import com.example.demowithtests.domain.workplace.Workplace;
import com.example.demowithtests.dto.workplaceDto.WorkplaceRequestDto;
import com.example.demowithtests.dto.workplaceDto.WorkplaceResponseDto;
import com.example.demowithtests.dto.workplaceDto.WorkplaceResponseDtoList;
import com.example.demowithtests.service.workplace.WorkplaceService;
import com.example.demowithtests.util.mapstruct.WorkplaceMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Workplace", description = "Workplace API")

public class WorkplaceControllerBean implements WorkplaceController {

    private final WorkplaceService workplaceService;
    private final WorkplaceMapper workplaceMapper;

    @Override
    @PostMapping("/workplace")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkplaceResponseDto saveWorkplace(@RequestBody WorkplaceRequestDto workplaceRequestDto) {
        log.debug("Controller --> saveWorkplace() - start: workplaceRequestDto = {}", workplaceRequestDto);
        Workplace workplace = workplaceService.create(workplaceMapper.fromWorkplaceRequestDto(workplaceRequestDto));
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.toWorkplaceResponseDto(workplace);
        log.debug("Controller --> saveWorkplace() - end: workplaceResponseDto = {}", workplaceResponseDto);
        return workplaceResponseDto;
    }
    @Transactional
    @Override
    @GetMapping(value = "/workplace/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkplaceResponseDto getWorkplaceById(Integer id){
        log.info("Controller --> getWorkplaceById() - start: id = {}", id);
        Workplace workplace = workplaceService.getById(id);
        WorkplaceResponseDto workplaceResponseDto = workplaceMapper.toWorkplaceResponseDto(workplace);
        log.info("Controller --> getWorkplaceById() - end: workplaceResponseDto = {}", workplaceResponseDto);
        return workplaceResponseDto;

    }


    @Override
    @PutMapping(value = "/workplace/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public WorkplaceResponseDto updateWorkplace(Integer id, Workplace newWorkplace) {
        if (!id.equals(newWorkplace.getId())) {
            throw new IllegalArgumentException("Invalid request: id in path and body do not match");
        }
        Workplace workplaceToUpdate = workplaceService.getById(id);
        if (workplaceToUpdate == null) {
            throw new EntityNotFoundException("Workplace with id " + id + " not found");
        }
        workplaceToUpdate.setName(newWorkplace.getName());
        workplaceToUpdate.setCapacity(newWorkplace.getCapacity());
        Workplace updatedWorkplace = workplaceService.update(workplaceToUpdate);
        return workplaceMapper.toWorkplaceResponseDto(updatedWorkplace);
    }
    @Override
    @GetMapping(value = "/workplace/available/{employeeSize}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public WorkplaceResponseDtoList getAvailableWorkplaces(Integer employeeSize) {
        log.debug("Controller --> getAvailableWorkplaces() - start: employeeSize = {}", employeeSize);
        List<Workplace> availableWorkplaces = workplaceService.getAvailableWorkplaces(employeeSize);
        List<WorkplaceResponseDto> availableWorkplaceResponseDtos = availableWorkplaces.stream()
                .map(workplaceMapper::toWorkplaceResponseDto)
                .collect(Collectors.toList());
        log.debug("Controller --> getAvailableWorkplaces() - end: availableWorkplaceResponseDtos = {}", availableWorkplaceResponseDtos);
        return new WorkplaceResponseDtoList(availableWorkplaceResponseDtos);
    }

}


