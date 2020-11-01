package new_tpl.kyle.config

import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.source.obj.struct.type.BaseClass

class MapperTemplateConfig extends AbstractOutTemplateConfig {

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/dao/${entity.getName()}Mapper.java"
    }

}
