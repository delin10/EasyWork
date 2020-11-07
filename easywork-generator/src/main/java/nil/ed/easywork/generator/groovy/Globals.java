package nil.ed.easywork.generator.groovy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author lidelin.
 */
public class Globals {

    public static final Map<String, Object> GROOVY_CXT = new HashMap<>();

    public static final String CFG_LIST = "configList";

    static {
        GROOVY_CXT.put(CFG_LIST, new LinkedList<>());
    }

}
