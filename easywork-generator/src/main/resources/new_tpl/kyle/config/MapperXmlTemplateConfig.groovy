package new_tpl.kyle.config


import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.source.obj.type.BaseClass

class MapperXmlTemplateConfig extends AbstractProcessListFieldTemplateConfig {

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/mapper_xml/${entity.getName()}Mapper.xml"
    }

}
