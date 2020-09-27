package new_tpl.kyle.config

import nil.ed.easywork.generator.config.AbstractTemplateConfig
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.generator.sql2java.DefaultSql2JavaGenerator
import nil.ed.easywork.source.obj.struct.BaseClass
import nil.ed.easywork.util.Utils

abstract class AbstractOutTemplateConfig extends AbstractTemplateConfig {

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        String afterRender = DefaultSql2JavaGenerator.INSTANCE.generate(context, template, config).get(0);
        BaseClass entity = context.get(GenerateContextBuilder.ENTITY) as BaseClass;
        Utils.writeToFile(config.getBasePath(), getFile(context, template, entity, config), afterRender);
    }

    abstract String getFile(Map<String, Object> context, String template, BaseClass entity, Config config);

}
