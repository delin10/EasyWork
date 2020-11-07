package nil.ed.easywork.generator.generator.sql2java.listener;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.util.ClassUtils;
import org.apache.commons.beanutils.ConstructorUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin.
 */
@Slf4j
public class ListenerContext {

    private static final Map<Class<?>, TemplateRenderListener> LISTENER = new HashMap<>();

    static {
        try {
            ClassUtils.loadAllClass("nil.ed.easywork.generator.generator.sql2java.listener", c -> {
                if (Modifier.isAbstract(c.getModifiers()) || Modifier.isInterface(c.getModifiers())) {
                    return;
                }
                Constructor<?> constructor = ConstructorUtils.getAccessibleConstructor(c, new Class<?>[]{});
                if (constructor == null) {
                    log.warn(c + " has no default constructor");
                    return;
                }
                constructor.setAccessible(true);
                Object obj = constructor.newInstance();
                if (obj instanceof TemplateRenderListener) {
                    register((TemplateRenderListener) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(TemplateRenderListener listener) {
        LISTENER.put(listener.getClass(), listener);
    }

    public static void invokeBeforeRender(Map<String, Object> context, String template, Config config) {
        LISTENER.values().forEach(listener -> listener.beforeRender(context, template, config));
    }

    public static String invokeAfterRender(Map<String, Object> context, String template, Config config, String afterRender) {
        for (TemplateRenderListener listener : LISTENER.values()) {
            afterRender = listener.afterRender(context, template, config, afterRender);
        }
        return afterRender;
    }

}
