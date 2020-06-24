package nil.ed.easywork.template;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public class TemplateEngineFactory {

    public static ITemplateEngineAdapter<IContext> getThymeLeafTextTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);
        templateEngine.setTemplateResolver(resolver);
        return new ThymeLeafTemplateEngineAdapter(templateEngine);
    }

}
