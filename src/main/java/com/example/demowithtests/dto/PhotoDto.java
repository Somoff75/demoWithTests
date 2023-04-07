package com.example.demowithtests.dto;

import java.time.Instant;
import java.util.Date;

public class PhotoDto {
    public String linkPhoto;

    public Integer length;

    public Integer width;

    public Date createdDate = Date.from(Instant.now());
}
