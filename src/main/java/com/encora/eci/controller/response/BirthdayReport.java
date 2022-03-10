package com.encora.eci.controller.response;

import com.encora.eci.persistance.model.Employee;

import java.util.List;

public class BirthdayReport {

    List<Employee> todaysList;
    List<Employee> nextWeekList;

    public List<Employee> getTodaysList() {
        return todaysList;
    }

    public void setTodaysList(List<Employee> todaysList) {
        this.todaysList = todaysList;
    }

    public List<Employee> getNextWeekList() {
        return nextWeekList;
    }

    public void setNextWeekList(List<Employee> nextWeekList) {
        this.nextWeekList = nextWeekList;
    }

    public BirthdayReport() {
    }

    public BirthdayReport(List<Employee> todaysList, List<Employee> nextWeekList) {
        this.todaysList = todaysList;
        this.nextWeekList = nextWeekList;
    }
}
