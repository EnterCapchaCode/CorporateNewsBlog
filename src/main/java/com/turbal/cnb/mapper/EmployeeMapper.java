/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */


package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "surname", source = "employee.surname")
    @Mapping(target = "login", source = "employee.login")
    @Mapping(target = "role", source = "employee.role")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "name", source = "employeeDto.name")
    @Mapping(target = "surname", source = "employeeDto.surname")
    @Mapping(target = "login", source = "employeeDto.login")
    @Mapping(target = "googleId", source = "employeeDto.googleId")
    @Mapping(target = "role", source = "employeeDto.role")
    Employee toEntity(EmployeeDto employeeDto);
}
