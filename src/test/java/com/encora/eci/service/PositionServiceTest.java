package com.encora.eci.service;

import com.encora.eci.controller.response.PositionsReport;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.EmployeeRepository;
import com.encora.eci.persistance.repository.PositionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PositionServiceTest {

    PositionRepository positionRepository;
    PositionService positionService;
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setMocks(){
        positionRepository = Mockito.mock(PositionRepository.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        positionService = new PositionService(positionRepository, employeeRepository);
    }

    @Test
    void asignPosition() {
        //Given
        Position position = new Position("Developer", 30000, 1,false);

        when(positionRepository.save(position)).thenReturn(position);
        //When
        Position result =positionService.asignPosition(position);

        //Then
        assertThat(result).isEqualTo(position);
    }

    @Test
    void disablePositionsFromEmployee() {
        //Given
        List<Position> positions = new ArrayList<>();
        Position position = new Position("Developer", 30000, 1,true);
        Position position2 = new Position("Developer", 30000, 1,false);
        positions.add(position2);
        positions.add(position);

        when(positionRepository.findPositionsByEmployeeId(1)).thenReturn(positions);
        //When
        positionService.disablePositionsFromEmployee(1);

        //Then
        assertThat(position.isActive()).isEqualTo(false);
        assertThat(position2.isActive()).isEqualTo(false);
    }

    @Test
    void generateReport(){
        //Given
        List<Position> positions = new ArrayList<>();
        Position position = new Position("Developer", 30000, 1,true);
        Position position2 = new Position("Developer", 30000, 2,true);
        Position position3 = new Position("Manager", 30000, 3,true);
        positions.add(position2);
        positions.add(position);
        positions.add(position3);

        when(positionRepository.findPositionsByActiveTrue()).thenReturn(positions);
        //When
        PositionsReport report = positionService.generateReport();

        //Then
        assertThat(report.getReport().get("Developer")).isEqualTo(2);
        assertThat(report.getReport().get("Manager")).isEqualTo(1);
    }

    @Test
    void generateSalaryReport(){
        //Given
        List<Position> positions = new ArrayList<>();
        Position position = new Position("Developer", 30000, 1,true);
        Position position2 = new Position("Developer", 30000, 2,true);
        Position position3 = new Position("Manager", 40000, 3,true);
        positions.add(position2);
        positions.add(position);
        positions.add(position3);

        when(positionRepository.findPositionsByActiveTrue()).thenReturn(positions);
        //When
        PositionsReport report = positionService.generateSalaryReport();

        //Then
        assertThat(report.getReport().get("30000")).isEqualTo(2);
        assertThat(report.getReport().get("40000")).isEqualTo(1);
    }
}