/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */


package com.turbal.cnb.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private RoleDto roleDto;
}
