package com.example.demowithtests.web.passport;

import com.example.demowithtests.domain.passport.Passport;
import com.example.demowithtests.dto.passportDto.PassportRequestDto;
import com.example.demowithtests.dto.passportDto.PassportResponseDto;
import com.example.demowithtests.dto.passportDto.ResidenceDto;
import com.example.demowithtests.service.passport.PassportService;
import com.example.demowithtests.util.mapstruct.PassportMapper;
import com.example.demowithtests.util.mapstruct.ResidenceMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassportControllerBean implements PassportController {

    private final PassportService passportService;
    private final PassportMapper passportMapper;
    private final ResidenceMapper residenceMapper;


    @Override
    public PassportResponseDto savePassport(PassportRequestDto requestDto) {
        Passport passport = passportMapper.fromRequestDto(requestDto);
        return passportMapper.toResponseDto(passportService.create(passport));
    }

    @Override
    public List<PassportResponseDto> getAllPassports() {
        List<Passport> passports = passportService.getAll();
        List<PassportResponseDto> responseDtos = new ArrayList<>();
        for (Passport passport : passports) {
            responseDtos.add(
                    passportMapper.toResponseDto(passport)
            );
        }
        return responseDtos;
    }

    @Override
    public PassportResponseDto getPassportById(Integer id) {
        Passport passport = passportService.getById(id);
        return passportMapper.toResponseDto(passport);
    }

    @Override
    public PassportResponseDto refreshPassport(Integer id, PassportRequestDto requestDto) {
        PassportResponseDto responseDto;
        responseDto = passportMapper
                .toResponseDto(passportService.updateById(id, passportMapper.fromRequestDto(requestDto)));
        return responseDto;

    }

    @Override
    public PassportResponseDto addResidenceToPassport(Integer id, ResidenceDto residenceDto) {
        log.info("Controller ==> addResidenceToPassport() - start: id = {} ", id);
        PassportResponseDto responseDto = passportMapper
                .toResponseDto(passportService
                        .addResidence(id, residenceMapper
                                .fromResidenceDto(residenceDto)));
        log.info("Controller ==> addResidenceToPassport() - is over: responseDto = {} ", responseDto);
        return responseDto;
    }

    @Override
    public List<PassportResponseDto> deactivateResidence(String country) {
        log.info("Controller ==> deactivateResidence() - start: country = {} ", country);
        List<PassportResponseDto> responseDtoList = new ArrayList<>();
        List<Passport> passports = passportService.deactivateResidence(country);
        for (Passport passport : passports) {
            responseDtoList.add(passportMapper.toResponseDto(passport));
        }
        log.info("Controller ==> deactivateResidence() - is over: ");
        return responseDtoList;
    }
    @Override
    public PassportResponseDto findPassportByResidence(UUID id) {
        log.info("Controller ==> findPassportByResidence() - start: id = {} ", id);
        PassportResponseDto responseDto = passportMapper
                .toResponseDto(passportService
                        .findByResidence(id));
        log.info("Controller ==> refreshPassport() - end: responseDto = {} ", responseDto);
        return responseDto;
    }


}
