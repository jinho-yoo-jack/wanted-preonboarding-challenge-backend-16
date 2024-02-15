package com.wanted.preonboarding.domain.dto;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DtoSchemaSerializer {
  public static Map<String, Object> getClassSchema(Class<?> cls) {
    return Arrays.stream(cls.getDeclaredFields())
        .collect(Collectors.toMap(
            Field::getName,
            field -> {
              Class<?> fieldType = field.getType();
              if (fieldType.isPrimitive() ||
                  String.class.isAssignableFrom(fieldType) ||
                  Number.class.isAssignableFrom(fieldType) ||
                  Boolean.class.isAssignableFrom(fieldType) ||
                  UUID.class.isAssignableFrom(fieldType)) {
                return fieldType.getSimpleName();
              } else {
                //  클래스라면 한 번 더 팜
                return getClassSchema(fieldType);
              }
            }
        ));
  }
}
