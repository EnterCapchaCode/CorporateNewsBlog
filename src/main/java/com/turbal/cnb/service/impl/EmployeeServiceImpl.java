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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) throws ValidationException {
        validateEmployeeDto(employeeDto);
        var employee = employeeMapper.toEntity(employeeDto);
        employeeRepo.save(employee);

        log.info("Employee with ID = {} saved", employeeDto.getId());
        return employeeDto;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeRepo.deleteById(id);
        log.info("Employee with ID = {} deleted", id);
    }

    @Override
    public EmployeeDto findByLogin(String login) {
        var employee = employeeRepo.findByLogin(login);
        if (employee != null) {
            return employeeMapper.toDto(employee);
        }
        return null;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepo.findAll()
            .stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toList());
    }

    private void validateEmployeeDto(EmployeeDto employeeDto) throws ValidationException {
        if (isNull(employeeDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(employeeDto.getLogin()) || employeeDto.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
        log.info("Employee with ID = {} passed validation", employeeDto.getId());
    }

}