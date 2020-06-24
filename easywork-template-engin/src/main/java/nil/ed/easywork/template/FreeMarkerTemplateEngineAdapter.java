package nil.ed.easywork.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author delin10
 * @since 2020/6/4
 **/
@Slf4j
public class FreeMarkerTemplateEngineAdapter implements ITemplateEngineAdapter<Object> {

    @Override
    public String process(String template, Object context) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        try {
            Template tmp = new Template("", template, cfg);
            StringWriter writer = new StringWriter();
            tmp.process(context, writer);
            return writer.getBuffer().toString();
        } catch (IOException | TemplateException e) {
            log.error("Failed to process template! {}", template, e);
        }
        return template;
    }

    @Override
    public ContextBuilder<Object> getContextBuilder() {
        return new FreeMarkerContextBuilder();
    }

}
