package nil.ed.easywork.generator.type;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public class TypeMapper {

    private static Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("ARRAY", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("BIGINT", //$NON-NLS-1$
                Long.class.getName());
        typeMap.put("BINARY", //$NON-NLS-1$
                "byte[]"); //$NON-NLS-1$
        typeMap.put("BIT", //$NON-NLS-1$
                Boolean.class.getName());
        typeMap.put("BLOB", //$NON-NLS-1$
                "byte[]"); //$NON-NLS-1$
        typeMap.put("BOOLEAN", //$NON-NLS-1$
                Boolean.class.getName());
        typeMap.put("CHAR", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("CLOB", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("DATALINK", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("DATE", //$NON-NLS-1$
                Date.class.getName());
        typeMap.put("DECIMAL", //$NON-NLS-1$
                BigDecimal.class.getName());
        typeMap.put("DISTINCT", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("DOUBLE", //$NON-NLS-1$
                Double.class.getName());
        typeMap.put("FLOAT", //$NON-NLS-1$
                Double.class.getName());
        typeMap.put("INTEGER", //$NON-NLS-1$
                Integer.class.getName());
        typeMap.put("JAVA_OBJECT", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("LONGNVARCHAR", //$NON-NLS-1$
                String.class.getName());
        typeMap.put(
                "LONGVARBINARY", //$NON-NLS-1$
                "byte[]"); //$NON-NLS-1$
        typeMap.put("LONGVARCHAR", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("NCHAR", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("NCLOB", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("NVARCHAR", //$NON-NLS-1$
                String.class.getName());
        typeMap.put("NULL", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("NUMERIC", //$NON-NLS-1$
                BigDecimal.class.getName());
        typeMap.put("OTHER", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("REAL", //$NON-NLS-1$
                Float.class.getName());
        typeMap.put("REF", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("SMALLINT", //$NON-NLS-1$
                Short.class.getName());
        typeMap.put("STRUCT", //$NON-NLS-1$
                Object.class.getName());
        typeMap.put("TIME", //$NON-NLS-1$
                Date.class.getName());
        typeMap.put("TIMESTAMP", //$NON-NLS-1$
                Timestamp.class.getName());
        typeMap.put("TINYINT", //$NON-NLS-1$
                Byte.class.getName());
        typeMap.put("VARBINARY", //$NON-NLS-1$
                "byte[]"); //$NON-NLS-1$
        typeMap.put("VARCHAR", //$NON-NLS-1$
                String.class.getName());
        // JDK 1.8 types
        typeMap.put("TIME_WITH_TIMEZONE", //$NON-NLS-1$
                "java.time.OffsetTime"); //$NON-NLS-1$
        typeMap.put("TIMESTAMP_WITH_TIMEZONE", //$NON-NLS-1$
                "java.time.OffsetDateTime"); //$NON-NLS-1$
    }

    public String map(String sqlType) {
        return typeMap.get(sqlType);
    }

}
