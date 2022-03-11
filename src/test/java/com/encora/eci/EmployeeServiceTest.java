package com.encora.eci;

import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.repository.AddressRepository;
import com.encora.eci.persistance.repository.EmployeeRepository;
import com.encora.eci.persistance.repository.PositionRepository;
import com.encora.eci.service.AddressService;
import com.encora.eci.service.EmployeeService;
import com.encora.eci.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PositionService positionService = new PositionService(positionRepository);
    @InjectMocks
    private AddressService addressService = new AddressService(addressRepository);
    @InjectMocks
    EmployeeService employeeService = new EmployeeService(employeeRepository, positionService, addressService);


    List<Employee> employees;

    @BeforeEach
    void setMockOutput(){
        employees = new ArrayList<>();
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, "Mexico", "Jalisco", 1));
        when(employeeRepository.findEmployeesByFirstNameContaining("")).thenReturn(employees);
    }

    @Test
    public void saveEmployee(){
        assertEquals(employees, employeeService.findByFirstNameAdmin(""));
    }
}
