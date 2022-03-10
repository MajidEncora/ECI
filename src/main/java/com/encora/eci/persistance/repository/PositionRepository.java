package com.encora.eci.persistance.repository;

import com.encora.eci.persistance.model.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository  extends CrudRepository<Position, Integer> {
    List<Position> findPositionsByEmployeeId(Integer employeeId);
    Position findPositionByEmployeeIdAndActiveTrue(Integer employeeId);
    List<Position> findPositionsByActiveTrue();
    List<Position> findPositionsByNameAndActiveTrue(String name);
    List<Position> findPositionsByNameAndActiveTrueOrTerminatedTrue(String name);
}
