package com.example.adambackend.controller.admin;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService service;

    @GetMapping("")
    public ResponseEntity<?> getStatistics() {
        return ResponseEntity.ok(service.getStatistics());
    }
}
