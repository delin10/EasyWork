package nil.ed.easywork.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/4
 **/
@Slf4j
public class FreeMarkerTemplateEngineAdapter implements ITemplateEngineAdapter<Object> {

    public static final FreeMarkerTemplateEngineAdapter INSTANCE = new FreeMarkerTemplateEngineAdapter();

    @Setter @Getter private Map<String, Object> vars;

    public FreeMarkerTemplateEngineAdapter() { }

    @Override
    public String process(String template, Object context) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        try {
            if (vars != null) {
                for (Map.Entry<String, Object> entry : vars.entrySet()) {
                    cfg.setSharedVariable(entry.getKey(), entry.getValue());
                }
            }
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
