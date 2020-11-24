package nil.ed.easywork.generator.generator.wiki.context;

import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.util.ClassUtils;
import org.apache.commons.beanutils.ConstructorUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class ResolveContext {

    public static final String BASE_PACKAGE_PARAM = "basePackage";

    public static final String CONTROLLERS = "controllers";

    public static final Map<String, Object> CXT = new HashMap<>();

    public static <T extends ResolveResult> T allocate(Class<T> clazz) {
        return ClassUtils.init(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(String key) {
        return (List<T>) CXT.computeIfAbsent(key, k -> new LinkedList<>());
    }

    public static String getString(String key) {
        return CXT.computeIfAbsent(key, k -> "").toString();
    }

}
