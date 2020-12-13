package new_tpl.kyle.config

import nil.ed.easywork.comment.enums.FunctionEnum
import nil.ed.easywork.comment.enums.OpType
import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.generator.singleton.BeanContext
import nil.ed.easywork.generator.util.FieldUtils
import nil.ed.easywork.source.obj.type.JavaType
import nil.ed.easywork.util.naming.NamingTranslatorSingleton
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream

abstract class AbstractProcessListFieldTemplateConfig extends AbstractOutTemplateConfig {
    final def PROCESSED_LIST_FIELD = "processedListFields";
    final def QUERY_STR = "listQueryStr";

    final Map<OpType, String> templateMap

    {
        templateMap = new HashMap<>();
        Stream.of(OpType.values()).filter(t -> t != OpType.LIKE)
                .forEach(op -> templateMap.put(op, "%s ${op.getOp()} #{%s}".toString()))
        templateMap.put(OpType.LIKE, "%s like concat('%%', #{query}, '%%')".toString());
    }


    @Override
    void doAction(Map<String, Object> context, String template, Config config) throws IOException {
        buildCxt(context, template, config)
        super.doAction(context, template, config)
        afterAction(context, template, config)
    }

    void buildCxt(Map<String, Object> context, String template, Config config) throws IOException {
        def descs = context.get(GenerateContextBuilder.FIELD_DESC) as List<CommentDescription>
        def listFieldValueList = new LinkedList<FieldValue>()
        List<String> queryList = new LinkedList<>()
        descs.stream().filter(f -> f.getFunc() == FunctionEnum.LIST).forEach(desc -> {
            def v = new FieldValue()
            if (desc.query) {
                String tpl = templateMap.get(desc.op)
                if (StringUtils.isNoneBlank(tpl)) {
                    queryList.add(String.format(tpl, desc.originName, desc.name))
                }
                return
            }
            v.isCollectionSuffix = FieldUtils.isCollectionSuffix(desc.name)
            v.noSuffixRealName = FieldUtils.cutCollectionSuffix(desc.name)
            v.noSuffixPascalName = NamingTranslatorSingleton.CAMEL_TO_PASCAL.trans(v.noSuffixRealName)
            v.colCamelName = NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(desc.originName)
            v.hasSuffixRealName = desc.name
            v.realType = JavaType.create(desc.type).getSimpleTypeName()
            v.listCollectionGenericType = BeanContext.TYPE_TOOL.getGenericType(desc.type)
            v.col = desc.originName
            v.colPascalName = NamingTranslatorSingleton.CAMEL_TO_PASCAL.trans(v.colCamelName)
            v.op = desc.op.op
            listFieldValueList.add(v)
        })
        String result = queryList.stream().collect(Collectors.joining(" or ", "(", ")"))
        if (CollectionUtils.isEmpty(queryList)) {
            result = ""

        }
        context.put(QUERY_STR, result)
        context.put(PROCESSED_LIST_FIELD, listFieldValueList)
    }

    void afterAction(Map<String, Object> context, String template, Config config) throws IOException {
        context.remove(PROCESSED_LIST_FIELD)
    }

    static class FieldValue {
        String colPascalName
        String colCamelName
        String hasSuffixRealName
        String noSuffixRealName
        String noSuffixPascalName
        String realType
        String listCollectionGenericType
        String col
        String op
        boolean isCollectionSuffix
    }
}


