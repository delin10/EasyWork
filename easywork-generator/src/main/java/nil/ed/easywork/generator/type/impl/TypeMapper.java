package nil.ed.easywork.generator.type.impl;

import nil.ed.easywork.generator.type.ITypeMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public class TypeMapper implements ITypeMapper {

    private static final Map<String, String> TYPE_MAP = new HashMap<>();

    static {
        TYPE_MAP.put("ARRAY",
                Object.class.getName());
        TYPE_MAP.put("BIGINT",
                Long.class.getName());
        TYPE_MAP.put("BINARY",
                "byte[]");
        TYPE_MAP.put("BIT",
                Boolean.class.getName());
        TYPE_MAP.put("BLOB",
                "byte[]");
        TYPE_MAP.put("BOOLEAN",
                Boolean.class.getName());
        TYPE_MAP.put("CHAR",
                String.class.getName());
        TYPE_MAP.put("CLOB",
                String.class.getName());
        TYPE_MAP.put("DATALINK",
                Object.class.getName());
        TYPE_MAP.put("DATE",
                LocalDate.class.getName());
        TYPE_MAP.put("DECIMAL",
                BigDecimal.class.getName());
        TYPE_MAP.put("DISTINCT",
                Object.class.getName());
        TYPE_MAP.put("DOUBLE",
                Double.class.getName());
        TYPE_MAP.put("FLOAT",
                Double.class.getName());
        TYPE_MAP.put("INTEGER",
                Integer.class.getName());
        TYPE_MAP.put("JAVA_OBJECT",
                Object.class.getName());
        TYPE_MAP.put("LONGNVARCHAR",
                String.class.getName());
        TYPE_MAP.put("LONGVARBINARY",
                "byte[]"); 
        TYPE_MAP.put("LONGVARCHAR",
                String.class.getName());
        TYPE_MAP.put("NCHAR",
                String.class.getName());
        TYPE_MAP.put("NCLOB",
                String.class.getName());
        TYPE_MAP.put("NVARCHAR",
                String.class.getName());
        TYPE_MAP.put("NULL",
                Object.class.getName());
        TYPE_MAP.put("NUMERIC",
                BigDecimal.class.getName());
        TYPE_MAP.put("OTHER",
                Object.class.getName());
        TYPE_MAP.put("REAL",
                Float.class.getName());
        TYPE_MAP.put("REF",
                Object.class.getName());
        TYPE_MAP.put("SMALLINT",
                Short.class.getName());
        TYPE_MAP.put("STRUCT",
                Object.class.getName());
        TYPE_MAP.put("TIME",
                Date.class.getName());
        TYPE_MAP.put("TIMESTAMP",
                Timestamp.class.getName());
        TYPE_MAP.put("TINYINT",
                Byte.class.getName());
        TYPE_MAP.put("VARBINARY", "byte[]");
        TYPE_MAP.put("VARCHAR", String.class.getName());
        TYPE_MAP.put("TIME_WITH_TIMEZONE", "java.time.OffsetTime");
        TYPE_MAP.put("TIMESTAMP_WITH_TIMEZONE", "java.time.OffsetDateTime");
    }

    @Override
    public String map(String sqlType) {
        return TYPE_MAP.get(sqlType);
    }

}
