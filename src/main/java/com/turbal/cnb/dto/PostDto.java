/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDto {

    private Integer id;
    private String title;
    private String description;
    private String text;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private Integer negativeRating;
    private Integer positiveRating;
    private EmployeeDto author;
}
