package nil.ed.easywork.generator.config;

import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;

import java.io.IOException;
import java.util.Map;

/**
 * @author lidelin.
 */
public interface TemplateAction {

    /**
     * Template Action.
     * @param context 上下文.
     * @param template 模版入参.
     * @param config 全局配置.
     * @throws IOException io.
     */
    default void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        FreeMarkerTemplateEngineAdapter adapter = new FreeMarkerTemplateEngineAdapter();
        String value = adapter.process(template, context);
        System.out.println(value);
    }

}
