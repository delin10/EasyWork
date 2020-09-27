package new_tpl.kyle.config

import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.source.obj.struct.BaseClass

class ServiceInterfaceTemplateConfig extends AbstractOutTemplateConfig {

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/service/${entity.getName()}Service.java"
    }

}
