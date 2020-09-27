package nil.ed.easywork.generator.freemarker;

import freemarker.core.Environment;
import freemarker.template.DefaultListAdapter;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.template.support.FreeMarkerSupport;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class JavaImportDirectiveTemplateModel implements TemplateDirectiveModel {

    private ThreadLocal<List<String>> imports = ThreadLocal.withInitial(LinkedList::new);

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String imp = String.valueOf(params.get("value"));
        String finalImp = FreeMarkerSupport.renderPlaceHolder(imp, env.getDataModel());
        @SuppressWarnings("unchecked")
        List<String> imports = FreeMarkerSupport.getList(GenerateContextBuilder.CURRENT_IMPORTS, env);
        imports.add(finalImp);
    }

}
