/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.exception.ValidationException;

import java.util.List;


public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto) throws ValidationException;

    void deleteEmployee(Integer id);

    List<EmployeeDto> findAll();

    List<EmployeeDto> findEmployeeByRequest(String request);
}
