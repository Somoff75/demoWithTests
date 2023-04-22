package com.example.demowithtests.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
@Entity
@Table(name = "photos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String linkPhoto;
    private Integer length;
    private Integer width;

    public Date creationTime = Date.from(Instant.now());
    private Boolean isVisible = Boolean.TRUE;

    public String getLinkPhoto(){
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public Integer getLength(){
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth(){
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Date getCreationTime(){
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Boolean getIsVisible(){
        return isVisible;

    }
    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}