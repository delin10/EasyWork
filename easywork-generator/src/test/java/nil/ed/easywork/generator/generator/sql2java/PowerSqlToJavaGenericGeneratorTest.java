package nil.ed.easywork.generator.generator.sql2java;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.type.impl.MyBatisColTypeTransformer;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.util.ClasspathFileUtils;
import org.junit.Test;

public class PowerSqlToJavaGenericGeneratorTest {

    private SQLFileProcessor processor = new SQLFileProcessor("/Users/admin/delin/sql/ad_creative.sql",
            new AdsTypeMapper(), new MyBatisColTypeTransformer());

    @Test
    public void generate() {
        Config config = new Config();
//        String basePkg = "com.kuaikan.ads.kyle.operation.log";
//        String basePkg = "com.kuaikan.ads.kyle.account.reserved";
        String basePkg = "com.kuaikan.ads.kyle.ad";
        String basePath = "/Users/admin/delin/generated/test_creative";
        config.setBasePkg(basePkg);
        config.setBasePath(basePath);
        config.setPrefix("");
        config.setProfile("kyle");
        PowerTemplateContext context = new PowerTemplateContext(ClasspathFileUtils.getClassPath("/new_tpl/" + config.getProfile()));

        PowerSqlToJavaGenericGenerator generator = new PowerSqlToJavaGenericGenerator(config, new FreeMarkerTemplateEngineAdapter(), processor);
        generator.generate(context);
    }

}