package new_tpl.kyle.config

import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.source.obj.type.BaseClass

class ConverterTemplateConfig extends AbstractOutTemplateConfig {

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/converter/${entity.getName()}Converter.java"
    }

}
