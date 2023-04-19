package com.example.demowithtests.dto.passportDto;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ResidenceDto {

    public Long id;
    public UUID residenceId = UUID.randomUUID();
    public String country;
    public String city;
    public String street;
    public Date activatedUntil;
    public Boolean addressHasActive = Boolean.TRUE;
    public Date residenceDate = Date.from(Instant.now());
}
