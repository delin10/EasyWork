package nil.ed.easywork.generator.generator.wiki.generator;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;
import nil.ed.easywork.generator.generator.wiki.resolver.ResolverPipeline;
import nil.ed.easywork.generator.singleton.BeanContext;
import nil.ed.easywork.template.ITemplateEngineAdapter;

import java.io.IOException;

/**
 * @author lidelin.
 */
public class Wiki2WebGenerator {

    private static final ITemplateEngineAdapter templateEngineAdapter = BeanContext.FREE_MARKER_TEMPLATE_ENGINE;

    public static void generate(String html, PowerTemplateContext context, Config config) {
        ResolverPipeline.process(html);
        context.getTemplateConfigCache().forEach((k, v) -> {
            try {
                v.doAction(ResolveContext.CXT, v.getTemplateText(), config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
