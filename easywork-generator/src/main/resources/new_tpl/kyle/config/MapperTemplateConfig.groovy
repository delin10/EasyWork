package new_tpl.kyle.config

import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.source.obj.struct.BaseClass

class MapperTemplateConfig extends AbstractOutTemplateConfig {

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/mapper/${entity.getName()}Mapper.java"
    }

}