package com.encora.eci.service;

import com.encora.eci.controller.response.*;
import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final PositionService positionService;
    private final AddressService addressService;

    public EmployeeService(EmployeeRepository employeeRepository, PositionService positionService, AddressService addressService) {
        this.employeeRepository = employeeRepository;
        this.positionService = positionService;
        this.addressService = addressService;
    }

    public GenderReport getGenderReport(){
        GenderReport report = new GenderReport();
        report.setMaleGender(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Male));
        report.setFemaleGender(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Female));
        report.setOtherGender(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Other));
        return report;
    }

    public Iterable getAllEmployees(){
        return employeeRepository.findAll();
    }

    public BirthdayReport getBirthdayReport(){
        LocalDate parsedBirthday = LocalDate.now();
        List<Employee> todaysList = employeeRepository.findByBirthday(parsedBirthday);
        LocalDate from = parsedBirthday.plusDays(1);
        LocalDate to = parsedBirthday.plusDays(6);
        List<Employee> nextWeekList = employeeRepository.findEmployeesByBirthdayBetween(from, to);
        return new BirthdayReport(todaysList, nextWeekList);
    }

    public DetailedEmployee getDetailedEmployee(Integer id, boolean admin) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty())
            return null;
        List<Position> positions = admin ? positionService.positionsByEmployee(employee.get().getId()) : new ArrayList<>();
        Optional<Address> addressOptional = addressService.getEmployeeAddress(employee.get().getAddressId());
        Address address = addressOptional.orElseGet(() -> new Address("N/A", "N/A", "N/A"));
        return new DetailedEmployee(employee.get(), address, positions);
    }

    public Employee findById(Integer id){
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public CRUDResponse deleteById(Integer id) {
        Optional<Employee> target = employeeRepository.findById(id);
        if(target.isPresent()){
            target.get().setDeletedAt(LocalDate.now());
            employeeRepository.save(target.get());
            positionService.terminateByEmployeeId(target.get().getId());
            return new CRUDResponse(true, "Employee deleted with id: "+id);
        }else{
            return new CRUDResponse(false, "Employee not found");
        }
    }

    public List<Employee> findByFirstName(String name){
        return employeeRepository.findEmployeesByFirstNameContainingAndDeletedAtNull(name);
    }

    public List<Employee> findByFirstNameAdmin(String name){
        return employeeRepository.findEmployeesByFirstNameContaining(name);
    }

    public List<Employee> findByLastName(String lastname){
        return employeeRepository.findEmployeesByLastNameContainingAndDeletedAtNull(lastname);
    }

    public List<Employee> findByLastNameAdmin(String lastname){
        return employeeRepository.findEmployeesByLastNameContaining(lastname);
    }

    public CountryReport generateCountryReport(){
        Map<String, List<Employee>> countryReport = new HashMap<>();
        Map<String, List<Employee>> stateReport = new HashMap<>();

        List<Employee> employees = employeeRepository.findEmployeesByDeletedAtNull();

        for(Employee employee : employees){
            String country = employee.getCountry();
            String state = employee.getState();

            countryReport.computeIfAbsent(country, k -> new ArrayList<>());
            stateReport.computeIfAbsent(state, k -> new ArrayList<>());

            countryReport.get(country).add(employee);
            stateReport.get(state).add(employee);
        }

        return new CountryReport(countryReport, stateReport);
    }

    public List<Employee> findByPosition(String positionName, boolean admin){
        List<Position> positions = admin ? positionService.getPositionsByNameAdmin(positionName) : positionService.getPositionsByName(positionName);
        List<Employee> employees = new ArrayList<>();

        for(Position position : positions){
            log.info(String.valueOf(position));
            Optional<Employee> target = employeeRepository.findById(position.getEmployeeId());
            if(target.isPresent())
                employees.add(target.get());
        }
        return employees;
    }
}
