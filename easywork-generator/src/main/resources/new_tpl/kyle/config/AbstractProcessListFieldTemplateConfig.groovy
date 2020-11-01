package new_tpl.kyle.config

import nil.ed.easywork.comment.enums.FunctionEnum
import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.singleton.BeanContext
import nil.ed.easywork.generator.util.FieldUtils

abstract class AbstractProcessListFieldTemplateConfig extends AbstractOutTemplateConfig {
    final def PROCESSED_LIST_FIELD = "processedListFields";

    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        def fields = context.get(FunctionEnum.LIST.name + GenerateContextBuilder.FUNC_FIELDS_SUFFIX) as List<String>
        def descMap = context.get(GenerateContextBuilder.FIELD_DESC) as Map<String, CommentDescription>
        def idDesc = new CommentDescription()
        idDesc.setName("idSet")
        idDesc.setOriginName("id")
        idDesc.setFunc(FunctionEnum.LIST)
        idDesc.setType("Set<Long>")
        descMap.put("id-list", idDescTIMESTAMP)
        def listFieldValueList = new LinkedList<FieldValue>()
        fields.forEach(f -> {
            def v = new FieldValue()
            def desc = descMap.get("${f}-${FunctionEnum.LIST.name}".toString()) as CommentDescription
            v.isCollectionSuffix = FieldUtils.isCollectionSuffix(desc.name)
            v.cutRealName = FieldUtils.cutCollectionSuffix(desc.name)
            v.camelName = f
            v.realName = desc.name
            v.type = desc.type
            v.generic = BeanContext.TYPE_TOOL.getGenericType(desc.type)
            v.col = desc.originName
            listFieldValueList.add(v)
        })
        context.put(PROCESSED_LIST_FIELD, listFieldValueList)
        super.doAction(context, template, config)
        context.remove(PROCESSED_LIST_FIELD)
    }

    class FieldValue {
        String camelName
        String realName
        String cutRealName
        String type
        String generic
        String col
        boolean isCollectionSuffix
    }
}
