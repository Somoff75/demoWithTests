package com.example.demowithtests.domain.passport;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "residences")
@Data
@Builder
@ToString


public class Residence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private UUID residenceId = UUID.randomUUID();

    private String country;
    private String city;
    private String street;
    private Date activatedUntil;

    private Boolean addressHasActive = Boolean.TRUE;
    private Date residenceDate = Date.from(Instant.now());


}
