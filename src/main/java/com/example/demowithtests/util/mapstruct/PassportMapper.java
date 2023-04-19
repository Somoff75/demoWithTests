package com.example.demowithtests.util.mapstruct;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.Residence;
import com.example.demowithtests.dto.passportDto.PassportRequestDto;
import com.example.demowithtests.dto.passportDto.PassportResponseDto;
import com.example.demowithtests.dto.passportDto.ResidenceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface PassportMapper {



    Passport fromRequestDto(PassportRequestDto requestDto);
    PassportResponseDto toResponseDto (Passport passport);
    Residence map(ResidenceDto residenceDto);
}
