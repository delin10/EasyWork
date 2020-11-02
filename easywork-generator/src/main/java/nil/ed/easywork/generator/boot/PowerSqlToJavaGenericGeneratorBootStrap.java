package nil.ed.easywork.generator.boot;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.generator.sql2java.PowerSqlToJavaGenericGenerator;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.type.impl.MyBatisColTypeTransformer;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.util.ClasspathFileUtils;

/**
 * @author lidelin.
 */
public class PowerSqlToJavaGenericGeneratorBootStrap {

    private static SQLFileProcessor processor = new SQLFileProcessor("/Users/admin/delin/sql/ad_material.sql",
            new AdsTypeMapper(), new MyBatisColTypeTransformer());

    public static void main(String[] args) {
        Config config = new Config();
//        String basePkg = "com.kuaikan.ads.kyle.operation.log";
//        String basePkg = "com.kuaikan.ads.kyle.account.reserved";
        String basePkg = "com.kuaikan.ads.kyle.ad";
//        String basePkg = "com.kuaikan.adsystem";
        String basePath = "/Users/admin/delin/ad_material";
        config.setBasePkg(basePkg);
        config.setBasePath(basePath);
        config.setPrefix("");
        config.setProfile("kyle");
        PowerTemplateContext context = new PowerTemplateContext(ClasspathFileUtils.getClassPath("/new_tpl/" + config.getProfile()));

        PowerSqlToJavaGenericGenerator generator = new PowerSqlToJavaGenericGenerator(config, new FreeMarkerTemplateEngineAdapter(), processor);
        generator.generate(context);
    }
}
