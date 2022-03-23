package com.encora.eci.controller;

import com.encora.eci.controller.exception.EmployeeNotFoundException;
import com.encora.eci.controller.response.*;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.service.EmployeeService;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public DetailedEmployee findOne(@PathVariable Integer id) {
        try{
            return employeeService.getDetailedEmployee(id, false);
        }catch (Exception e){
            throw new EmployeeNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<Employee>> findBy(@RequestParam(required = false) String name, @RequestParam(required = false) String lastName) {
        List<Employee> result;
        try {
            if (name!=null && lastName!=null) {
                result = employeeService.findByNameAndLastName(name, lastName);
            } else if (name!=null) {
                result = employeeService.findByFirstName(name);
            } else if (lastName!=null) {
                result = employeeService.findByLastName(lastName);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findByFirstName/{name}")
    public List<Employee> findByFirstName(@PathVariable String name) {
        return employeeService.findByFirstName(name);
    }

    @GetMapping("/findByLastName/{lastname}")
    public List<Employee> findByLastName(@PathVariable String lastname) {
        return employeeService.findByLastName(lastname);
    }

    @GetMapping("/findByPosition/{name}")
    public List<Employee> findByPosition(@PathVariable String name) {
        return employeeService.findByPosition(name, false);
    }

}
