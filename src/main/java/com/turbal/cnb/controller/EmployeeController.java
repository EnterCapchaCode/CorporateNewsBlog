/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.findAll();
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{request}")
    private List<EmployeeDto> findEmployeeByRequest(@PathVariable String request) {
        return employeeService.findEmployeeByRequest(request);
    }

    @PutMapping("/setCreator/{id}")
    private ResponseEntity<Void> setToEmployeeCreatorRole(@PathVariable Integer id) {
        employeeService.setCreatorRole(id);
        return ResponseEntity.ok().build();
    }
}
