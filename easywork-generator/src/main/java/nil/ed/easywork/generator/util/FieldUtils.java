package nil.ed.easywork.generator.util;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lidelin.
 */
@Slf4j
public class FieldUtils {

    private static final Set<String> S_SUFFIX_WHITELIST = new HashSet<>();
    private static final String S_SUFFIX_WHITELIST_FILE = "/s.suffix.whitelist";

//    static {
//        try {
//            Utils.listLines(FieldUtils.class.getResource(S_SUFFIX_WHITELIST_FILE).getFile(), line -> {
//                if (StringUtils.isBlank(line)) {
//                    return;
//                }
//                String trimLine = line.trim();
//                S_SUFFIX_WHITELIST.add(trimLine);
//            }, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            log.error("Failed to load {}!", S_SUFFIX_WHITELIST_FILE, e);
//        }
//    }

    public static boolean isCollectionSuffix(String name) {
        return name.endsWith("Set")
                || name.endsWith("List")
                || name.endsWith("Collection")
                || (name.endsWith("s") && !S_SUFFIX_WHITELIST.contains(name));
    }

    public static String cutCollectionSuffix(String name) {
        String suffix = "";
        if (name.endsWith("Set")) {
            suffix = "Set";
        } else if (name.endsWith("List")) {
            suffix = "List";
        } else if (name.endsWith("Collection")) {
            suffix = "Collection";
        } else if (name.endsWith("s") && !S_SUFFIX_WHITELIST.contains(name)) {
            suffix = "s";
        }
        return name.substring(0, name.length() - suffix.length());
    }

}
