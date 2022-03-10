package com.encora.eci.service;

import com.encora.eci.controller.response.PositionsReport;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PositionService {

    private static final Logger log = LoggerFactory.getLogger(PositionService.class);
    private PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position asignPosition(Position position){
        disablePositionsFromEmployee(position.getEmployeeId());
        position.setActive(true);
        return positionRepository.save(position);
    }

    public Optional<Position> getById(Integer id){
        Optional<Position> target = positionRepository.findById(id);
        return target;
    }

    public void terminateByEmployeeId(Integer employeeId){
        Position position = positionRepository.findPositionByEmployeeIdAndActiveTrue(employeeId);
        if(position!=null){
            position.setTerminated(true);
            position.setActive(false);
            positionRepository.save(position);
        }
    }

    public void disablePositionsFromEmployee(Integer employeeId){
        List<Position> positionsFromSameEmployee = positionRepository.findPositionsByEmployeeId(employeeId);

        for (Position positionFromSameEmployee: positionsFromSameEmployee){
            positionFromSameEmployee.setActive(false);
            positionRepository.save(positionFromSameEmployee);
        }
    }

    public PositionsReport generateReport(){
        List<Position> activePositions = positionRepository.findPositionsByActiveTrue();
        Map<String, Integer> report = new HashMap<>();

        for(Position position : activePositions){
            String name = position.getName();
            int count = report.getOrDefault(name, 0);
            report.put(name, count + 1);
        }

        return new PositionsReport(report);
    }

    public PositionsReport generateSalaryReport(){
        List<Position> activePositions = positionRepository.findPositionsByActiveTrue();
        Map<String, Integer> report = new HashMap<>();

        for(Position position : activePositions){
            String name = String.valueOf(position.getSalary());
            int count = report.getOrDefault(name, 0);
            report.put(name, count + 1);
        }

        return new PositionsReport(report);
    }

    public List<Position> getPositionsByName(String name){
        return positionRepository.findPositionsByNameAndActiveTrue(name);
    }

    public List<Position> getPositionsByNameAdmin(String name){
        return positionRepository.findPositionsByNameAndActiveTrueOrTerminatedTrue(name);
    }
}
