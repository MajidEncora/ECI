package com.encora.eci.persistance.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotNull
    private Integer salary;

    @Column(nullable = false)
    private Integer employeeId;

    @Column
    private boolean active;

    @Column
    private boolean terminated;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public Position(String name, Integer salary, Integer employeeId, boolean active) {
        this.name = name;
        this.salary = salary;
        this.employeeId = employeeId;
        this.active = active;
    }

    protected Position() { }

    public Integer getId() {
        return id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public boolean isActive() {
        return active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return String.format(
                "Position[id=%d, name='%s', salary='%s', employeeId='%s', active='%s', terminated='%s']",
                id, name, salary, employeeId, active, terminated);
    }
}
