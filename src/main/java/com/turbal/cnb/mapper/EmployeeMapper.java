/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */


package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.EmployeeDto;
import com.turbal.cnb.dto.RoleDto;
import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface EmployeeMapper {

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "surname", source = "employee.surname")
    @Mapping(target = "login", source = "employee.login")
    @Mapping(target = "password", source = "employee.password")
    @Mapping(target = "roleDto", source = "employee.role")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "name", source = "employeeDto.name")
    @Mapping(target = "surname", source = "employeeDto.surname")
    @Mapping(target = "login", source = "employeeDto.login")
    @Mapping(target = "password", source = "employeeDto.password")
    @Mapping(target = "role", source = "employeeDto.roleDto")
    Employee toEntity(EmployeeDto employeeDto);
}
