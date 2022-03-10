package com.encora.eci.persistance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String corporateEmail;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated
    @Column(nullable = false)
    private GenderTypes gender;

    @Column
    private String personalEmail;

    @Column
    private String phone;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Integer addressId;

    public String getCorporateEmail() {
        return corporateEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column
    private LocalDate deletedAt;

    protected Employee() {
        super();
    }

    public Employee(String corporateEmail, String firstName, String lastName, GenderTypes gender, String country, String state, Integer addressId) {
        this.corporateEmail = corporateEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.state = state;
        this.country = country;
        this.addressId = addressId;
    }

    public Employee(String corporateEmail, String firstName, String lastName, GenderTypes gender, String country, String state, Integer addressId, String personalEmail, String phone, String birthday) {
        this.corporateEmail = corporateEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personalEmail = personalEmail;
        this.phone = phone;
        this.state = state;
        this.country = country;
        this.addressId = addressId;
        this.birthday = LocalDate.parse(birthday);
    }


    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDate getCreated_at() {
        return createdAt;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer address_id) {
        this.addressId = address_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personal_email) {
        this.personalEmail = personal_email;
    }

    public GenderTypes getGender() {
        return gender;
    }

    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', corporate_email='%s', gender='%s', birthday='%s']",
                id, firstName, lastName, corporateEmail, gender, birthday);
    }
}

