package com.wanted.preonboarding.domain.common.utils;

import java.util.List;
import java.util.Objects;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;

public class QueryUtils {


    public static <T extends Enum<T>> BooleanExpression inEnumListIfNotNull (EnumPath<T> path, List<T> e) {

        return Objects.isNull(e) ? null : path.in(e);
    }



}