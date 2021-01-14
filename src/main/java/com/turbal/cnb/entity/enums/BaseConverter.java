/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.entity.enums;

import com.turbal.cnb.exception.TechnicalException;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseConverter<E extends BaseEnum<E>> implements AttributeConverter<E, Integer> {

    private final Map<Integer, E> mappings;
    private final String enumName;

    protected BaseConverter(Class<E> eClass) {
        mappings = buildMappings(eClass);
        enumName = eClass.getSimpleName();
    }

    @Override
    public final Integer convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public final E convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Optional.ofNullable(mappings.get(dbData))
            .orElseThrow(() -> new TechnicalException(enumName + " not found for code " + dbData, null));
    }

    private Map<Integer, E> buildMappings(Class<E> eClass) {
        return Arrays.stream(eClass.getEnumConstants())
            .collect(Collectors.toMap(BaseEnum::getCode, Function.identity()));
    }
}
