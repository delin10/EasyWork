package nil.ed.easywork.generator.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import lombok.SneakyThrows;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.template.support.FreeMarkerSupport;
import ognl.Ognl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class JavaImportsDirectiveTemplateModel implements TemplateDirectiveModel {

    private ThreadLocal<List<String>> imports = ThreadLocal.withInitial(LinkedList::new);

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String imp = String.valueOf(params.get("value"));
        Object cxt = FreeMarkerSupport.getRawContext(env);
        List<String> imports = (List<String>) Ognl.getValue(imp, cxt);
        List<String> contextImports = FreeMarkerSupport.getList(GenerateContextBuilder.CURRENT_IMPORTS, env);
        contextImports.addAll(imports);
    }

}
