package nil.ed.easy.script.groovy.support;

import groovy.lang.GroovyClassLoader;

/**
 * @author lidelin.
 */
public class GroovyUtils {

    public static Class<?> parseClass(String script) {
        return new GroovyClassLoader().parseClass(script);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String script) {
        try {
            Class<?> clazz = parseClass(script);
            return (T) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Groovy config Instantiation error!" + script, e);
        }
    }

}
