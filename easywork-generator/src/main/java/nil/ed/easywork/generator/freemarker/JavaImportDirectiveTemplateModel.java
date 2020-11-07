package nil.ed.easywork.generator.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import lombok.SneakyThrows;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.template.support.FreeMarkerSupport;
import ognl.Ognl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class JavaImportDirectiveTemplateModel implements TemplateDirectiveModel {

    @SneakyThrows
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String imp = String.valueOf(params.get("value"));
        String finalImp = FreeMarkerSupport.renderPlaceHolder(imp, env.getDataModel());
        Object cxt = FreeMarkerSupport.getRawContext(env);
        Config config = (Config) Ognl.getValue(GenerateContextBuilder.ROOT, cxt);
        List<ImportItem> imports = FreeMarkerSupport.getList(GenerateContextBuilder.CURRENT_IMPORTS, env);
        imports.add(new ImportItem(config.getType(finalImp)));
    }

}
