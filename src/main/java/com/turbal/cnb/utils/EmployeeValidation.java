/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.utils;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class EmployeeValidation {

    public void validate(EmployeeDto employeeDto) throws ValidationException {
        if (isNull(employeeDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(employeeDto.getLogin()) || employeeDto.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
        log.info("Employee with ID = {} passed validation", employeeDto.getId());
    }
}
