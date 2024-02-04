package com.wanted.preonboarding.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ReflectionUtils {

    private ReflectionUtils() {
        throw new AssertionError();
    }

    // 상위 클래스의 필드에 값을 할당한다.
    public static <T, V> void setSuperClassField(T target, String fieldName, V value) {
        try {
            Field field = getFieldByName(target, fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // 모든 필드에서 주어진 이름에 해당하는 Field를 가져온다.
    private static <T> Field getFieldByName(T target, String fieldName) {
        Objects.requireNonNull(target);

        for (Field f : getAllFields(target)) {
            if (f.getName().equals(fieldName)) {
                return f;
            }
        }
        throw new RuntimeException();
    }

    // 모든 필드를 가져온다.
    private static <T> List<Field> getAllFields(T target) {
        Objects.requireNonNull(target);

        Class<?> clazz = target.getClass();
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
