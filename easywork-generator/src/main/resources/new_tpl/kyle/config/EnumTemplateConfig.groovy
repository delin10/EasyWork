package new_tpl.kyle.config

import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.AbstractTemplateConfig
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.generator.enums.EnumsGenerator
import nil.ed.easywork.util.Utils

class EnumTemplateConfig extends AbstractTemplateConfig {

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        EnumsGenerator generator = new EnumsGenerator((String afterRender, CommentDescription desc) -> {
           Utils.writeToFile(config.getBasePath(), "/enums/${desc.getName()}.java", afterRender)
        });
        generator.generate(context, template, config);
    }
}
