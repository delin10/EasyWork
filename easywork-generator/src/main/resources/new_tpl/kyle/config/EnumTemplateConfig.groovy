package new_tpl.kyle.config

import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.generator.enums.EnumsGenerator
import nil.ed.easywork.generator.generator.sql2java.listener.ListenerContext
import nil.ed.easywork.source.obj.type.BaseClass
import nil.ed.easywork.util.Utils

class EnumTemplateConfig extends AbstractCommonTemplateConfig {

    @Override
    void doActionInternal(Map<String, Object> context, String template, Config config) throws IOException {
        EnumsGenerator generator = new EnumsGenerator((String afterRender, CommentDescription desc) -> {
            String fixedRender = ListenerContext.invokeAfterRender(context, template, config, afterRender)
            if (fixedRender != null) {
                afterRender = fixedRender
            }
            Utils.writeToFile(config.getBasePath(), "/enums/${desc.getName()}.java", afterRender)
        })
        generator.generate(context, template, config)
    }

    @Override
    void registerClazz(Map<String, Object> context, String template, BaseClass entity, Config config) {
        EnumsGenerator generator = new EnumsGenerator((String afterRender, CommentDescription desc) -> {
            registerOneClazz(context, "enums.${desc.getName()}")
        });
        generator.generate(context, template, config);
    }
}
