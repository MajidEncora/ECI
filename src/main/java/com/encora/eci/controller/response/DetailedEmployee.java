package com.encora.eci.controller.response;

import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.Position;

import java.util.List;

public class DetailedEmployee {
    private Employee employee;
    private List<Position> positions;

    public DetailedEmployee(Employee employee, List<Position> positions) {
        this.employee = employee;
        this.positions = positions;
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
