package com.encora.eci.controller.response;

import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.Position;

import java.util.List;

public class DetailedEmployee {
    private Employee employee;
    private Address address;
    private List<Position> positions;

    public DetailedEmployee(Employee employee, Address address, List<Position> positions) {
        this.employee = employee;
        this.address = address;
        this.positions = positions;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
