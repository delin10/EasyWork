package nil.ed.easywork.generator.boot;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.generator.wiki.generator.Wiki2WebGenerator;
import nil.ed.easywork.util.ClasspathFileUtils;
import nil.ed.easywork.util.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author lidelin.
 */
public class Wiki2WebGeneratorBootstrap {
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        String basePkg = "com.kuaikan.ads.enterprise.market";
        String basePath = "/Users/admin/delin/generated/enterprise_market/wiki";
        config.setBasePkg(basePkg);
        config.setBasePath(basePath);
        config.setPrefix("");
        config.setProfile("");
        String html = Utils.readText("/Users/admin/delin/wiki/test", StandardCharsets.UTF_8);
        PowerTemplateContext context = new PowerTemplateContext(ClasspathFileUtils.getClassPath("/wiki/" + config.getProfile()));
        Wiki2WebGenerator.generate(html, context, config);
    }
}
