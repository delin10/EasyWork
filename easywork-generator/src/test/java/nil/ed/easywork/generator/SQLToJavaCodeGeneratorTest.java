package nil.ed.easywork.generator;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.generator.generator.SQLToJavaCodeGenerator;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.type.impl.MyBatisColTypeTransformer;
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

    private SQLFileProcessor processor = new SQLFileProcessor("/Users/admin/delin/sql/ad_creative.sql",
            new AdsTypeMapper(), new MyBatisColTypeTransformer());

    @Test
    public void generate() {
        TemplateEngine engine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.TEXT);
        engine.setTemplateResolver(resolver);
        ITemplateEngineAdapter<IContext> adapter = new ThymeLeafTemplateEngineAdapter(engine);
        Config config = new Config();
//        String basePkg = "com.kuaikan.ads.kyle.operation.log";
//        String basePkg = "com.kuaikan.ads.kyle.account.reserved";
        String basePkg = "com.kuaikan.ads.kyle.ad";
        String basePath = "/Users/admin/delin/generated/creative";
        config.setBasePkg(basePkg);
        config.setBasePath(basePath);
        config.setPrefix("");
        config.setProfile("kyle");
        TemplateContext context = new TemplateContext(ClasspathFileUtils.getClassPath("/tpl/" + config.getProfile()));

        SQLToJavaCodeGenerator generator = new SQLToJavaCodeGenerator(config, new FreeMarkerTemplateEngineAdapter(), processor);
        generator.generate(context);
    }
}