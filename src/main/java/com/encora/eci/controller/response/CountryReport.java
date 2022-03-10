package com.encora.eci.controller.response;

import com.encora.eci.persistance.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryReport {
    private Map<String, List<Employee>> countryReport = new HashMap<>();
    private Map<String, List<Employee>> StateReport = new HashMap<>();

    public Map<String, List<Employee>> getCountryReport() {
        return countryReport;
    }

    public void setCountryReport(Map<String, List<Employee>> countryReport) {
        this.countryReport = countryReport;
    }

    public Map<String, List<Employee>> getStateReport() {
        return StateReport;
    }

    public void setStateReport(Map<String, List<Employee>> stateReport) {
        StateReport = stateReport;
    }

    public CountryReport(Map<String, List<Employee>> countryReport, Map<String, List<Employee>> stateReport) {
        this.countryReport = countryReport;
        StateReport = stateReport;
    }

}
