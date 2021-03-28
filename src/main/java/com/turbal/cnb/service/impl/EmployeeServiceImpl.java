/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.exception.ValidationException;
import com.turbal.cnb.mapper.EmployeeMapper;
import com.turbal.cnb.repository.EmployeeRepo;
import com.turbal.cnb.service.EmployeeService;
import com.turbal.cnb.utils.EmployeeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;
    private final EmployeeValidation employeeValidation;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) throws ValidationException {
        employeeValidation.validate(employeeDto);
        var employee = employeeMapper.toEntity(employeeDto);
        employeeRepo.save(employee);

        log.info("Employee with login = {} saved", employeeDto.getLogin());
        return employeeDto;
    }

    @Override
    @Transactional
    public void setCreatorRole(Integer id) {
        employeeRepo.setCreatorRole(id);
        log.info("Set CREATOR role to employee with ID = {}", id);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepo.deleteById(id);
        log.info("Employee with ID = {} deleted", id);
    }

    @Override
    public List<EmployeeDto> findAll() {
        log.info("Got a list of all employees");
        return employeeRepo.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findEmployeeByRequest(String request) {
        log.info("Got a list of employees by request = {}", request);
        return employeeRepo.findEmployeeByRequest(request)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}
