/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.utils;

public final class ErrorCodes {
    private ErrorCodes() {
        throw new IllegalStateException("Trying to invoke private constructor.");
    }

    // EMPLOYEE
    public static final String EMPLOYEE_EMAIL_NOT_NULL = "error.validation.employee.email.notNull";
    public static final String EMPLOYEE_EMAIL_NOT_EMPTY = "error.validation.employee.email.notEmpty";
}
