package nil.ed.easywork.generator.type.impl;

import nil.ed.easywork.source.obj.type.JavaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin
 */
public class UsualJavaTypeCache {

    private static final Map<Class<?>, JavaType> CACHE = new HashMap<>(32, 1);

    static {
        CACHE.put(Integer.class, JavaType.create(Integer.class.getCanonicalName()));
        CACHE.put(Long.class, JavaType.create(Long.class.getCanonicalName()));
        CACHE.put(Double.class, JavaType.create(Double.class.getCanonicalName()));
        CACHE.put(Object.class, JavaType.create(Object.class.getCanonicalName()));
        CACHE.put(String.class, JavaType.create(String.class.getCanonicalName()));
        CACHE.put(BigDecimal.class, JavaType.create(BigDecimal.class.getCanonicalName()));

        CACHE.put(LocalDate.class, JavaType.create(LocalDate.class.getCanonicalName()));
        CACHE.put(LocalDateTime.class, JavaType.create(LocalDateTime.class.getCanonicalName()));
        CACHE.put(byte[].class, JavaType.create("byte[]"));
    }

    public static JavaType getType(Class<?> clazz) {
        return CACHE.get(clazz);
    }

}
