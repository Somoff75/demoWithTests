package com.example.demowithtests.util.mapstruct;

import com.example.demowithtests.domain.passport.Residence;
import com.example.demowithtests.dto.passportDto.ResidenceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidenceMapper {

    Residence fromResidenceDto(ResidenceDto residenceDto);

    ResidenceDto toResidenceDto(Residence residence);


}
