package com.encora.eci.controller;

import com.encora.eci.controller.response.PositionsReport;
import com.encora.eci.persistance.model.Position;
import com.encora.eci.service.PositionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Position create(@RequestBody Position position) {
        return positionService.asignPosition(position);
    }

    @GetMapping("/admin/positionReport")
    public PositionsReport positionReport() {
        return positionService.generateReport();
    }

    @GetMapping("/admin/salaryReport")
    public PositionsReport salaryReport() {
        return positionService.generateSalaryReport();
    }
}
