/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */


package com.turbal.cnb.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.turbal.cnb.utils.ErrorCodes.EMPLOYEE_EMAIL_NOT_EMPTY;
import static com.turbal.cnb.utils.ErrorCodes.EMPLOYEE_EMAIL_NOT_NULL;

@Data
public class EmployeeDto {

    private Integer id;
    private String name;
    private String surname;
    @NotNull(message = EMPLOYEE_EMAIL_NOT_NULL)
    @NotEmpty(message = EMPLOYEE_EMAIL_NOT_EMPTY)
    private String login;
    private String password;
    private RoleDto roleDto;
}
