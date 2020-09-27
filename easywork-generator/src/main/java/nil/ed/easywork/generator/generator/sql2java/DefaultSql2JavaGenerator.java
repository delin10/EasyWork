package nil.ed.easywork.generator.generator.sql2java;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.singleton.BeanContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class DefaultSql2JavaGenerator implements Sql2JavaGenerator {

    public static final DefaultSql2JavaGenerator INSTANCE = new DefaultSql2JavaGenerator();

    @Override
    public List<Object> generate(Map<String, Object> cxt, String template, Config config) {
        return Collections.singletonList(BeanContext.FREE_MARKER_TEMPLATE_ENGINE.process(template, cxt));
    }

}
