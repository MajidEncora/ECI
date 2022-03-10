package com.encora.eci.persistance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String street;
    private String number;
    private String zipCode;

    protected Address(){}

    public Address(String street, String number, String zipCode) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
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
                "Address[id=%d, street='%s', number='%s', zipCode='%s']",
                id, street, number, zipCode);
    }
}
