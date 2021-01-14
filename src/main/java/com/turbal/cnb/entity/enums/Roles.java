/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.entity.enums;

public enum Roles implements BaseEnum<Roles> {
    ADMIN(0),
    CREATOR(1),
    READER(2);

    private final int code;

    Roles(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    public static class Converter extends BaseConverter<Roles> {
        public Converter() {
            super(Roles.class);
        }
    }
}
