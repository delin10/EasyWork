package nil.ed.easywork.generator.util.sorter;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import nil.ed.easywork.source.obj.type.ImportItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lidelin
 */
public class CheckStyleImportSortUtils {

    public static final String THIRD_PARTY = "THIRD";
    public static final String JAVA_PARTY = "JAVA";

    private static final int DEFAULT_PRIORITY = Integer.MIN_VALUE;
    private static final Map<String, Integer> IMPORT_PRIORITY_MAP = new HashMap<>();

    private static final Set<String> JAVA_PARTY_PKG_PREFIX = Sets.newHashSet("java", "javax");

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

    public static void sort(List<ImportItem> ls) {
        ls.sort((a, b) -> {
            int result = Integer.compare(getPriority(a.getContent()), getPriority(b.getContent()));
            if (result == 0) {
                return MoreObjects.firstNonNull(a.getContent(), "").compareTo(b.getContent());
            }
            return result;
        });
    }

    public static Map<String, List<ImportItem>> sortAndClassify(List<ImportItem> ls) {
        Map<String, List<ImportItem>> map = ls.stream()
                .collect(Collectors.groupingBy(e -> isJavaParty(e.getContent()) ? JAVA_PARTY : THIRD_PARTY));
        map.forEach((k, v) -> sort(v));
        return map;
    }

    public static boolean isJavaParty(String pkg) {
        return JAVA_PARTY_PKG_PREFIX.stream()
                .anyMatch(pkg::startsWith);
    }

}
