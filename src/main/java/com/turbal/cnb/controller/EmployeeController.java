/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.exception.ValidationException;
import com.turbal.cnb.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) throws ValidationException {
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("{login}")
    private EmployeeDto findByLogin(@PathVariable String login) {
        return employeeService.findByLogin(login);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
