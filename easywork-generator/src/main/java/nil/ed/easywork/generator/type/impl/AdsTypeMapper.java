package nil.ed.easywork.generator.type.impl;

import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.source.obj.struct.JavaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin
 */
public class AdsTypeMapper implements ITypeMapper {

    private static final Map<String, JavaType> TYPE_MAP = new HashMap<>();

    static {
        batchPut(UsualJavaTypeCache.getType(Object.class),
                "ARRAY", "DATALINK", "DISTINCT", "JAVA_OBJECT", "NULL", "OTHER", "REF", "STRUCT");
        batchPut(UsualJavaTypeCache.getType(String.class),
                "CHAR", "CLOB", "LONGNVARCHAR", "NCHAR", "NCLOB", "NVARCHAR", "VARCHAR");
        batchPut(UsualJavaTypeCache.getType(Double.class),
                "DOUBLE", "FLOAT");
        batchPut(UsualJavaTypeCache.getType(Long.class), "BIGINT");
        batchPut(UsualJavaTypeCache.getType(Integer.class),
                "INTEGER", "SMALLINT", "TINYINT");
        batchPut(UsualJavaTypeCache.getType(byte[].class),
                "BINARY", "BLOB", "LONGVARBINARY", "VARBINARY");
        batchPut(UsualJavaTypeCache.getType(Boolean.class),
                "BIT", "BOOLEAN");
        batchPut(UsualJavaTypeCache.getType(BigDecimal.class),
                "DECIMAL", "NUMERIC");
        batchPut(UsualJavaTypeCache.getType(LocalDate.class),
                "DATE");
        batchPut(UsualJavaTypeCache.getType(LocalTime.class),
                "TIME");
        batchPut(UsualJavaTypeCache.getType(LocalDateTime.class),
                "TIMESTAMP");
        batchPut(UsualJavaTypeCache.getType(OffsetTime.class),
                "TIME_WITH_TIMEZONE");
        batchPut(UsualJavaTypeCache.getType(OffsetDateTime.class),
                "TIMESTAMP_WITH_TIMEZONE");
    }

    private static void batchPut(JavaType type, String...dbTypes) {
        Arrays.stream(dbTypes)
                .distinct()
                .forEach(dbType -> TYPE_MAP.put(dbType, type));
    }

    @Override
    public String map(String type) {
        return TYPE_MAP.getOrDefault(type.toUpperCase(),
                UsualJavaTypeCache.getType(Object.class)).getFullyName();
    }
}
