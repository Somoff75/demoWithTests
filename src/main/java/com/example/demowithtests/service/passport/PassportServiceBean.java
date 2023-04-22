package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.passport.Passport;
import com.example.demowithtests.domain.passport.Residence;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.IdEmployeeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j

public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;



    @Override
    public Passport create(Passport passport) {
        return passportRepository.save(passport);
    }

    @Override
    public List<Passport> getAll() {
        return passportRepository.findAll();
    }

    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id)
                .orElseThrow(IdEmployeeNotFoundException::new);
    }


    @Override
    public Passport updateById(Integer id, Passport passport) {
        Passport entity = passportRepository.findById(id)
                .orElseThrow(IdEmployeeNotFoundException::new);
        entity.setFirstName(passport.getFirstName());
        entity.setSecondName(passport.getSecondName());
        return passportRepository.save(entity);
    }

    @Override
    public  Passport addResidence(Integer id, Residence residence){
        log.debug("Service --> addResidence() - start: id = {}, residence = {}", id, residence);
        Passport passport = passportRepository.findById(id).orElseThrow(IdEmployeeNotFoundException::new);
        Set<Residence> residenceSet = passport.getResidence();
        residenceSet.add(residence);
        passport.setResidence(residenceSet);
        log.debug("Service --> addResidence() - end: passport = {}", passport);
        return passportRepository.save(passport);

    }
    @Override
    public List<Passport> deactivateResidence(String country) {
        log.debug("--> deactivateResidence() - start: country = {}", country);
        List<Passport> passports = passportRepository.findPassportsWithCountry(country);
        for (Passport passport: passports) {
            passport.getResidence()
                    .forEach(residence -> residence.setAddressHasActive(Boolean.FALSE));
        }
        log.debug("--> deactivateResidence() - start: passports = {}", passports);
        return passportRepository.saveAll(passports);
    }
    @Override
    public Passport findByResidence(UUID id) {
        Passport passport = passportRepository.findPassportByResidenceId(id);
        return passport;
    }

}
