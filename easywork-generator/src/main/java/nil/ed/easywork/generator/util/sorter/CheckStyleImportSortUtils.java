package nil.ed.easywork.generator.util.sorter;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin
 */
public class CheckStyleImportSortUtils {

    private static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
    private static final Map<String, Integer> IMPORT_PRIORITY_MAP = new HashMap<>();

    static {
        IMPORT_PRIORITY_MAP.put("java.", 0);
        IMPORT_PRIORITY_MAP.put("javax.", 1);
    }

    private static int getPriority(String oneImport) {
        for (Map.Entry<String, Integer> entry : IMPORT_PRIORITY_MAP.entrySet()) {
            if (oneImport.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return DEFAULT_PRIORITY;

    }

    public static void sort(List<String> ls) {
        ls.sort((a, b) -> {
            int result = Integer.compare(getPriority(a), getPriority(b));
            if (result == 0) {
                return MoreObjects.firstNonNull(a, "").compareTo(b);
            }
            return result;
        });
    }

}
