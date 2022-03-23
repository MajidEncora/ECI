package com.encora.eci.controller.response;

import com.encora.eci.persistance.model.Employee;

import java.util.List;
import java.util.Map;

public class CountryReport {

    private Map<String, Map<String, Integer>> report;

    public CountryReport(Map<String, Map<String, Integer>> report) {
        this.report = report;
    }

    public Map<String, Map<String, Integer>> getReport() {
        return report;
    }

    public void setReport(Map<String, Map<String, Integer>> report) {
        this.report = report;
    }
}
