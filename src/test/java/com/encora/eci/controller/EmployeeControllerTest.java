package com.encora.eci.controller;

import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private static final String BASE_BATCH_URL = "/api/employees";
    @MockBean
    EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {

        //Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, "Mexico", "Jalisco", 1));
        when(employeeService.getAllEmployees()).thenReturn(employees);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_BATCH_URL))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}