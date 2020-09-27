package nil.ed.easywork.generator.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lidelin.
 */
public class TypeTool implements Tool {

    @Override
    public String getName() {
        return "TYPE_TOOL";
    }

    private static final Pattern GENERIC_PATTERN = Pattern.compile("<(.*?)>");
    public String getGenericType(String type) {
        Matcher matcher = GENERIC_PATTERN.matcher(type);
        String generic = type;
        if (matcher.find()) {
            generic = matcher.group(1);
        }
        return generic;
    }

}
