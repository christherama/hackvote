package com.teamtreehouse.hackvote.core;

import java.util.Arrays;

public class JsonUtils {
    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
        if(enumClass == null) {
            throw new IllegalArgumentException("enumClass value cannot be null");
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .filter(ev -> ev.toString().equalsIgnoreCase(value))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(value + "is an invalid value."));
    }
}
