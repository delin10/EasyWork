package nil.ed.easywork.generator;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.generator.generator.SQLToJavaCodeGenerator;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.template.ITemplateEngineAdapter;
import nil.ed.easywork.template.ThymeLeafTemplateEngineAdapter;
import nil.ed.easywork.util.ClasspathFileUtils;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

public class SQLToJavaCodeGeneratorTest {

    private SQLFileProcessor processor = new SQLFileProcessor("/Users/admin/delin/sql/summary");

    @Test
    public void generate() {
        TemplateEngine engine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);
        engine.setTemplateResolver(resolver);
        ITemplateEngineAdapter<IContext> adapter = new ThymeLeafTemplateEngineAdapter(engine);

        TemplateContext context = new TemplateContext(ClasspathFileUtils.getClassPath("/tpl"));
        Config config = new Config();
        config.setBasePkg("com.kuaikan.ads.kyle.finance");
        config.setBasePath("/Users/admin/delin/generated");
        config.setPrefix("");
        SQLToJavaCodeGenerator generator = new SQLToJavaCodeGenerator(config, new FreeMarkerTemplateEngineAdapter(), processor);
        generator.generate(context);
    }
}