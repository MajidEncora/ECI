package com.encora.eci.controller.response;

import java.util.Map;

public class PositionsReport {
    private Map<String, Integer> report;

    public PositionsReport(Map<String, Integer> report) {
        this.report = report;
    }

    public Map<String, Integer> getReport() {
        return report;
    }

    public void setReport(Map<String, Integer> report) {
        this.report = report;
    }
}
