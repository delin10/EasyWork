package new_tpl.kyle.config

import nil.ed.easywork.comment.enums.FunctionEnum
import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.singleton.BeanContext
import nil.ed.easywork.generator.util.FieldUtils
import nil.ed.easywork.source.obj.type.BaseClass

class MapperXmlTemplateConfig extends AbstractProcessListFieldTemplateConfig {

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        List<String> listFields = context.get(FunctionEnum.LIST.name + GenerateContextBuilder.FUNC_FIELDS_SUFFIX) as List<String>
        if (listFields != null && listFields.size() > 0) {
            if (!listFields.contains("id")) {
                listFields.add("id")
            }
        }
        def fields = context.get(FunctionEnum.SEARCH.name + GenerateContextBuilder.FUNC_FIELDS_SUFFIX) as List<String>
        def descMap = context.get(GenerateContextBuilder.FIELD_DESC) as Map<String, CommentDescription>
        def listFieldValueList = new LinkedList<FieldValue>()
        fields.forEach(f -> {
            def v = new FieldValue()
            def desc = descMap.get("${f}-${FunctionEnum.SEARCH.name}".toString()) as CommentDescription
            v.isCollectionSuffix = FieldUtils.isCollectionSuffix(desc.name)
            v.cutRealName = FieldUtils.cutCollectionSuffix(desc.name)
            v.camelName = f
            v.realName = desc.name
            v.type = desc.type
            v.generic = BeanContext.TYPE_TOOL.getGenericType(desc.type)
            v.col = desc.originName
            listFieldValueList.add(v)
        })
        context.put("processedSearchFields", listFieldValueList)
        super.doAction(context, template, config)
    }

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/mapper_xml/${entity.getName()}Mapper.xml"
    }

}
