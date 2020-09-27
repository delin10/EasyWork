package nil.ed.easywork.sql.utils;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author lidelin.
 */
public class SqlUtils {

    private static final Set<String> SQL_WRAP_CHARS = Sets.newHashSet("`");
    private static final Set<String> TEXT_WRAP_CHARS = Sets.newHashSet("'", "\"");

    public static String unwrapSqlNaming(String name) {
        if (SQL_WRAP_CHARS.contains(name.substring(0, 1))) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    public static String unwrapTextNaming(String name) {
        if (TEXT_WRAP_CHARS.contains(name.substring(0, 1))) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

}
