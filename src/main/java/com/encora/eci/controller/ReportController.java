package com.encora.eci.controller;

import com.encora.eci.controller.response.BirthdayReport;
import com.encora.eci.controller.response.CountryReport;
import com.encora.eci.controller.response.GenderReport;
import com.encora.eci.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/reports")
public class ReportController {

    private final EmployeeService employeeService;

    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/birthdayReport")
    public BirthdayReport findByBirthday() {
        return employeeService.getBirthdayReport();
    }

    @GetMapping("/genderReport")
    public GenderReport genderReport() {
        return employeeService.getGenderReport();
    }

    @GetMapping("/countryReport")
    public CountryReport countryReport() {
        return employeeService.generateCountryReport();
    }
}
