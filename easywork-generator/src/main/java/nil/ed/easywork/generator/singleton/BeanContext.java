package nil.ed.easywork.generator.singleton;

import nil.ed.easywork.generator.freemarker.JavaImportDirectiveTemplateModel;
import nil.ed.easywork.generator.freemarker.JavaImportWriteDirectiveTemplateModel;
import nil.ed.easywork.generator.freemarker.JavaImportsDirectiveTemplateModel;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin.
 */
public class BeanContext {

    public static final FreeMarkerTemplateEngineAdapter FREE_MARKER_TEMPLATE_ENGINE = new FreeMarkerTemplateEngineAdapter();

    static {
        Map<String, Object> vars = new HashMap<>();
        vars.put("JavaImportIn", new JavaImportDirectiveTemplateModel());
        vars.put("JavaImportOut", new JavaImportWriteDirectiveTemplateModel());
        vars.put("JavaImportsIn", new JavaImportsDirectiveTemplateModel());
        FREE_MARKER_TEMPLATE_ENGINE.setVars(vars);
    }

}
