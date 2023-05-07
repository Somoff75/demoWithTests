package com.example.demowithtests.service.passport;

import com.example.demowithtests.domain.passport.Passport;
import com.example.demowithtests.domain.passport.Residence;

import java.util.List;
import java.util.UUID;

public interface PassportService {

    Passport create(Passport passport);

    List<Passport> getAll();

    Passport getById(Integer id);

    Passport updateById(Integer id, Passport passport);


    Passport addResidence(Integer id, Residence residence);

    List<Passport> deactivateResidence(String country);

    Passport findByResidence(UUID id);
}