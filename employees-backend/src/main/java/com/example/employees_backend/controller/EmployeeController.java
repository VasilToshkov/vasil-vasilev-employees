package com.example.employees_backend.controller;

import com.example.employees_backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/getCommonProjects")
    public ResponseEntity<?> commonProject(@RequestBody MultipartFile file) throws IOException, DataFormatException {
        return this.employeeService.getAllCommonProjects(file);
    }

    @PostMapping("/bestPair")
    public ResponseEntity<?> bestPair(@RequestBody MultipartFile file) throws IOException, DataFormatException {
        return this.employeeService.getPairWithMostCommonDays(file);
    }
}
