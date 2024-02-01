package com.wanted.preonboarding.util;

import java.util.concurrent.ConcurrentHashMap;

public class StringUtil {

    public static Object getKeyFromValue(ConcurrentHashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}
