package com.encora.eci.service;

import com.encora.eci.controller.response.BirthdayReport;
import com.encora.eci.controller.response.CountryReport;
import com.encora.eci.controller.response.DetailedEmployee;
import com.encora.eci.controller.response.GenderReport;
import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    EmployeeRepository employeeRepository;
    PositionService positionService;
    AddressService addressService;
    EmployeeService employeeService;

    @BeforeEach
    void setMocks(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        positionService = Mockito.mock(PositionService.class);
        addressService = Mockito.mock(AddressService.class);
        employeeService = new EmployeeService(employeeRepository, positionService, addressService);
    }

    @Test
    void getGenderReport() {
        //Given
        when(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Male)).thenReturn(2L);
        when(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Female)).thenReturn(3L);
        when(employeeRepository.countByGenderAndDeletedAtNull(GenderTypes.Other)).thenReturn(1L);

        //When
        GenderReport report = employeeService.getGenderReport();

        //Then
        assertThat(report.getFemaleGender()).isEqualTo(3L);
        assertThat(report.getMaleGender()).isEqualTo(2L);
        assertThat(report.getOtherGender()).isEqualTo(1L);
    }

    @Test
    void getBirthdayReport() {
        //Given
        List<Employee> todaysList = new ArrayList<>();
        List<Employee> nextWeekList = new ArrayList<>();
        todaysList.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        nextWeekList.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        nextWeekList.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        LocalDate parsedBirthday = LocalDate.now();

        //When
        when(employeeRepository.findByBirthday(parsedBirthday)).thenReturn(todaysList);
        when(employeeRepository.findEmployeesByBirthdayBetween(parsedBirthday.plusDays(1), parsedBirthday.plusDays(6))).thenReturn(nextWeekList);
        BirthdayReport report = employeeService.getBirthdayReport();

        //Then
        assertThat(report.getTodaysList().size()).isEqualTo(1);
        assertThat(report.getNextWeekList().size()).isEqualTo(2);
    }

    @Test
    void getDetailedEmployee() {
        //Given
        List<Position> positions = new ArrayList<>();
        Position position = new Position("Developer", 30000, 1,false);
        Position position2 = new Position("Developer", 35000, 1,true);
        positions.add(position2);
        positions.add(position);
        Address address = new Address("street", "111", "33445", "Mexico", "Jalisco");
        Employee employee = new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 11);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(positionService.positionsByEmployee(null)).thenReturn(positions);
        when(addressService.getEmployeeAddress(11)).thenReturn(Optional.of(address));

        //When
        DetailedEmployee detailedEmployeeAdmin = employeeService.getDetailedEmployee(1, true);
        DetailedEmployee detailedEmployee = employeeService.getDetailedEmployee(1, false);

        //Then
        assertThat(detailedEmployeeAdmin.getEmployee()).isEqualTo(employee);
        assertThat(detailedEmployeeAdmin.getAddress()).isEqualTo(address);
        assertThat(detailedEmployeeAdmin.getPositions()).isEqualTo(positions);
        assertThat(detailedEmployee.getPositions().size()).isEqualTo(0);
    }

    @Test
    void generateCountryReport() {
        //Given
        Address address = new Address("street", "111", "33445", "Mexico", "Jalisco");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        when(employeeRepository.findEmployeesByDeletedAtNull()).thenReturn(employees);
        when(addressService.getEmployeeAddress(1)).thenReturn(Optional.of(address));

        //When
        CountryReport countryReport = employeeService.generateCountryReport();

        //Then
        assertThat(countryReport.getReport().get("Mexico")).isEqualTo(4);
    }
}