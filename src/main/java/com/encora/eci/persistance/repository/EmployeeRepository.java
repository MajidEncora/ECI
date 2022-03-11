package com.encora.eci.persistance.repository;

import com.encora.eci.persistance.model.Employee;
import com.encora.eci.persistance.model.GenderTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByBirthday(LocalDate birthday);
    List<Employee> findEmployeesByBirthdayBetween(LocalDate from, LocalDate to);
    long countByGenderAndDeletedAtNull(GenderTypes gender);
    List<Employee> findEmployeesByFirstNameContaining(String name);
    List<Employee> findEmployeesByLastNameContaining(String name);
    List<Employee> findEmployeesByFirstNameContainingAndDeletedAtNull(String name);
    List<Employee> findEmployeesByLastNameContainingAndDeletedAtNull(String lastname);
    List<Employee> findEmployeesByDeletedAtNull();
}
