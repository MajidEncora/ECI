package com.encora.eci.controller;

import com.encora.eci.controller.exception.EmployeeIdMismatchException;
import com.encora.eci.controller.exception.EmployeeNotFoundException;
import com.encora.eci.controller.response.*;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.repository.EmployeeRepository;
import com.encora.eci.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Iterable findAll(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/admin/birthdayReport")
    public BirthdayReport findByBirthday() {
        return employeeService.getBirthdayReport();
    }

    @GetMapping("/{id}")
    public DetailedEmployee findOne(@PathVariable Integer id) {
        try{
            return employeeService.getDetailedEmployee(id, false);
        }catch (Exception e){
            throw new EmployeeNotFoundException();
        }
    }

    @GetMapping("/admin/{id}")
    public DetailedEmployee findOneAdmin(@PathVariable Integer id) {
        try{
            return employeeService.getDetailedEmployee(id, true);
        }catch (Exception e){
            throw new EmployeeNotFoundException();
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/admin/{id}")
    public CRUDResponse delete(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Integer id) {
        if (employee.getId() != id) {
            throw new EmployeeIdMismatchException();
        }
        employeeService.findById(id);
        return employeeService.save(employee);
    }

    @GetMapping("/admin/genderReport")
    public GenderReport genderReport() {
        return employeeService.getGenderReport();
    }

    @GetMapping("/admin/countryReport")
    public CountryReport countryReport() {
        return employeeService.generateCountryReport();
    }

    @GetMapping("/findByFirstName/{name}")
    public List<Employee> findByFirstName(@PathVariable String name) {
        return employeeService.findByFirstName(name);
    }

    @GetMapping("/findByLastName/{lastname}")
    public List<Employee> findByLastName(@PathVariable String lastname) {
        return employeeService.findByLastName(lastname);
    }

    @GetMapping("/admin/findByFirstName/{name}")
    public List<Employee> findByFirstNameAdmin(@PathVariable String name) {
        return employeeService.findByFirstNameAdmin(name);
    }

    @GetMapping("/admin/findByLastName/{lastname}")
    public List<Employee> findByLastNameAdmin(@PathVariable String lastname) {
        return employeeService.findByLastNameAdmin(lastname);
    }

    @GetMapping("/findByPosition/{name}")
    public List<Employee> findByPosition(@PathVariable String name) {
        return employeeService.findByPosition(name, false);
    }

    @GetMapping("/admin/findByPosition/{name}")
    public List<Employee> findByPositionAdmin(@PathVariable String name) {
        return employeeService.findByPosition(name, true);
    }
}
