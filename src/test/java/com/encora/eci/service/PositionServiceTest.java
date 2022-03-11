package com.encora.eci.service;

import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.PositionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PositionServiceTest {

    PositionRepository positionRepository;
    PositionService positionService;

    @BeforeEach
    void setMocks(){
        positionRepository = Mockito.mock(PositionRepository.class);
        positionService = new PositionService(positionRepository);
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
    void getById() {
    }

    @Test
    void positionsByEmployee() {
    }

    @Test
    void disablePositionsFromEmployee() {
    }
}