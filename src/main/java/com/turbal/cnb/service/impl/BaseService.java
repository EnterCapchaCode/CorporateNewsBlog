/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.exception.ResourceNotFoundException;
import com.turbal.cnb.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public abstract class BaseService {

    @Autowired
    protected EmployeeRepo employeeRepo;

    protected Employee getCurrentEmployee() {
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> op = employeeRepo.findEmployeeByLogin(currentPrincipalName);

        return op.orElseThrow(() -> new ResourceNotFoundException("cannot find employee by email " + currentPrincipalName));
    }
}
