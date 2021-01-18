/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Integer id;
    private String commentText;
    private EmployeeDto employeeDto;
    private PostDto postDto;
}
