package com.encora.eci.controller;

import com.encora.eci.controller.response.BirthdayReport;
import com.encora.eci.controller.response.CountryReport;
import com.encora.eci.controller.response.DetailedEmployee;
import com.encora.eci.controller.response.GenderReport;
import com.encora.eci.persistance.model.Address;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        employees.add(new Employee("corpo@email.com", "Jack", "Bauer", GenderTypes.Male, 1));
        when(employeeService.getAllEmployees()).thenReturn(employees);

        //When
        MockHttpServletResponse response = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_BATCH_URL))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void findByBirthday() {
        //Given
        BirthdayReport report = new BirthdayReport(new ArrayList<>(), new ArrayList<>());
        when(employeeService.getBirthdayReport()).thenReturn(report);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/birthdayReport"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        //Then
        JSONObject jo = null;
        try {
            assert response != null;
            jo = new JSONObject(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        try {
            assertThat(jo.get("todaysList")).isNotNull();
            assertThat(jo.get("nextWeekList")).isNotNull();
        } catch (JSONException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void findOne() throws JSONException {
        //Given
        Employee employee = createEmployee();
        Address address = createAddress();
        List< Position > positions = new ArrayList<>();
        DetailedEmployee detailedEmployee = new DetailedEmployee(employee, address, positions);
        when(employeeService.getDetailedEmployee(1, true)).thenReturn(detailedEmployee);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/1"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONObject jo = null;
        try {
            assert response != null;
            jo = new JSONObject(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.get("address")).isNotNull();
        assertThat(jo.get("employee")).isNotNull();
        assertThat(jo.get("positions")).isNotNull();
    }

    @Test
    public void create() throws JSONException {
        //Given
        Employee employee = createEmployee();
        when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee);

        //When
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(BASE_BATCH_URL+"/admin")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                                    .content(asJsonString(employee)))
                                    .andExpect(status().isCreated())
                                    .andExpect(MockMvcResultMatchers.jsonPath("$.corporateEmail").exists());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
    }

    @Test
    public void genderReport() throws JSONException {
        //Given
        GenderReport genderReport = new GenderReport();
        genderReport.setOtherGender(3);
        genderReport.setFemaleGender(2);
        genderReport.setMaleGender(1);
        when(employeeService.getGenderReport()).thenReturn(genderReport);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/genderReport"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONObject jo = null;
        try {
            assert response != null;
            jo = new JSONObject(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.get("maleGender")).isNotNull();
        assertThat(jo.get("femaleGender")).isNotNull();
        assertThat(jo.get("otherGender")).isNotNull();
    }

    @Test
    public void countryReport() throws JSONException {
        //Given
        CountryReport countryReport = new CountryReport(new HashMap<>());
        when(employeeService.generateCountryReport()).thenReturn(countryReport);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/countryReport"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONObject jo = null;
        try {
            assert response != null;
            jo = new JSONObject(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.get("report")).isNotNull();
    }

    @Test
    public void findByFirstNameAdmin(){
        //Given
        List<Employee> employees = createList();
        when(employeeService.findByFirstNameAdmin("Juan")).thenReturn(employees);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/findByFirstName/Juan"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONArray jo = null;
        try {
            assert response != null;
            jo = new JSONArray(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.length()).isEqualTo(2);
    }

    @Test
    public void findByLastNameAdmin(){
        //Given
        List<Employee> employees = createList();
        when(employeeService.findByLastNameAdmin("Juan")).thenReturn(employees);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/findByLastName/Juan"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONArray jo = null;
        try {
            assert response != null;
            jo = new JSONArray(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.length()).isEqualTo(2);
    }

    @Test
    public void findByPositionAdmin(){
        //Given
        List<Employee> employees = createList();
        when(employeeService.findByPosition("Developer", true)).thenReturn(employees);

        //When
        MockHttpServletResponse response = null;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.get(BASE_BATCH_URL + "/admin/findByPosition/Developer"))
                    .andReturn().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Then
        JSONArray jo = null;
        try {
            assert response != null;
            jo = new JSONArray(response.getContentAsString());
        } catch (JSONException | UnsupportedEncodingException e) {
            Assertions.fail(e.getMessage());
        }
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(jo).isNotNull();
        assertThat(jo.length()).isEqualTo(2);
    }

    private List<Employee> createList(){
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployee());
        employees.add(createEmployee());
        return employees;
    }

    private Employee createEmployee(){
        return new Employee("corpo@email.com", "Juan", "Bauer", GenderTypes.Male, 1);
    }

    private Address createAddress(){
        return new Address("Street", "1234", "55667", "Mexico", "Jalisco");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}