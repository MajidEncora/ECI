package com.encora.eci.persistance.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String zipCode;

    @Column(nullable = false)
    @NotBlank
    private String state;

    @Column(nullable = false)
    @NotBlank
    private String country;

    protected Address(){}

    public Address(String street, String number, String zipCode, String country, String state) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return String.format(
                "Address[id=%d, street='%s', number='%s', zipCode='%s', state='%s', country='%s']",
                id, street, number, zipCode, state, country);
    }
}
