/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {

    private Integer id;
    private String commentText;
    private LocalDate creationDate;
    private EmployeeDto employeeDto;
    private PostDto postDto;
}
