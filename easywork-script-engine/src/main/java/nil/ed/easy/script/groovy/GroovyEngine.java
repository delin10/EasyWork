package nil.ed.easy.script.groovy;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easy.script.ScriptEngine;
import nil.ed.easy.script.groovy.support.GroovyUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * groovy脚本引擎.
 * @author lidelin.
 */
@Slf4j
public class GroovyEngine implements ScriptEngine {

    @Override
    @SuppressWarnings("rawtypes")
    public void executeConsumeMethod(String script, String method, Object... args) {
        try {
            Class<?> clazz = GroovyUtils.parseClass(script);
            Object obj = clazz.newInstance();
            Class[] argClazzs = Arrays.stream(args)
                    .map(Object::getClass)
                    .toArray(Class[]::new);
            Method m = clazz.getDeclaredMethod(method, argClazzs);
            m.invoke(obj, args);
        } catch (Exception e) {
            log.error("", e);
            System.exit(1);
        }
    }

}
