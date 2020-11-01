package nil.ed.easywork.template;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import java.lang.reflect.Method;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public class ThymeLeafTemplateEngineAdapter implements ITemplateEngineAdapter<IContext> {

    private ITemplateEngine engine;

    public ThymeLeafTemplateEngineAdapter(ITemplateEngine engine) {
        this.engine = engine;
    }

    @Override
    public String process(String template, IContext context) {
        return engine.process(template, context);
    }

    @Override
    public ContextBuilder<IContext> getContextBuilder() {
        return null;
    }
}
