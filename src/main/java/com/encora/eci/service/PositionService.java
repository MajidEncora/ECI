package com.encora.eci.service;

import com.encora.eci.controller.exception.EmployeeNotFoundException;
import com.encora.eci.controller.response.PositionsReport;
import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.persistance.repository.EmployeeRepository;
import com.encora.eci.persistance.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PositionService {

    private static final Logger log = LoggerFactory.getLogger(PositionService.class);
    private PositionRepository positionRepository;
    private EmployeeRepository employeeRepository;

    public PositionService(PositionRepository positionRepository, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    @Transactional
    public Position asignPosition(Position position){
        Optional<Employee> employee = employeeRepository.findById(position.getEmployeeId());
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException();
        }
        disablePositionsFromEmployee(position.getEmployeeId());
        position.setActive(true);
        return positionRepository.save(position);
    }

    public void terminateByEmployeeId(Integer employeeId){
        Position position = positionRepository.findPositionByEmployeeIdAndActiveTrue(employeeId);
        if(position!=null){
            position.setTerminated(true);
            position.setActive(false);
            positionRepository.save(position);
        }
    }

    public List<Position> positionsByEmployee(Integer employeeId){
        return positionRepository.findPositionsByEmployeeId(employeeId);
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
        return positionRepository.findPositionsByNameIgnoreCaseAndActiveTrue(name);
    }

    public List<Position> getPositionsByNameAdmin(String name){
        return positionRepository.findPositionsByNameIgnoreCaseAndActiveTrueOrTerminatedTrue(name);
    }
}
