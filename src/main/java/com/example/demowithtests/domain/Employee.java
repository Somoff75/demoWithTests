package com.example.demowithtests.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Data
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String country;
    private String email;
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Set<Photo> photos = new HashSet<>();


    public Employee(String name, String country, String email) {
        this.name = name;
        this.country = country;
        this.email = email;

    }
    public Employee() {
    }
    public Employee(Integer id, String name, String country, String email, Boolean isDeleted, Set<Address> addresses, Set<Photo> photos) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.email = email;
        this.isDeleted = isDeleted;
        this.addresses = addresses;
        this.photos = photos;
    }


    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                ", addresses=" + addresses +
                ", photos=" + photos +
                '}';

    }
}