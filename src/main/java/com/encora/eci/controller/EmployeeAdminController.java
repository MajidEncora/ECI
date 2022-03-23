package com.encora.eci.controller;

import com.encora.eci.controller.exception.EmployeeNotFoundException;
import com.encora.eci.controller.response.CRUDResponse;
import com.encora.eci.controller.response.DetailedEmployee;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/api/employees")
public class EmployeeAdminController {

    private final EmployeeService employeeService;

    public EmployeeAdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
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
    public Employee create(@Valid @RequestBody DetailedEmployee detailedEmployee) {
        return employeeService.createEmployee(detailedEmployee);
    }

    @DeleteMapping("/{id}")
    public CRUDResponse delete(@PathVariable Integer id) {
        return employeeService.deleteById(id);
    }

    @PutMapping("/")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.findById(employee.getId());
        return employeeService.save(employee);
    }

    @GetMapping("/findByFirstName/{name}")
    public List<Employee> findByFirstNameAdmin(@PathVariable String name) {
        return employeeService.findByFirstNameAdmin(name);
    }

    @GetMapping("/findByLastName/{lastname}")
    public List<Employee> findByLastNameAdmin(@PathVariable String lastname) {
        return employeeService.findByLastNameAdmin(lastname);
    }

    @GetMapping("/findByPosition/{name}")
    public List<Employee> findByPositionAdmin(@PathVariable String name) {
        return employeeService.findByPosition(name, true);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Employee>> findBy(@RequestParam(required = false) String name, @RequestParam(required = false) String lastName) {
        List<Employee> result;
        try {
            if (name!=null && lastName!=null) {
                result = employeeService.findByNameAndLastNameAdmin(name, lastName);
            } else if (name!=null) {
                result = employeeService.findByFirstNameAdmin(name);
            } else if (lastName!=null) {
                result = employeeService.findByLastNameAdmin(lastName);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
