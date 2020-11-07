package new_tpl.kyle.config


import nil.ed.easywork.comment.obj.CommentDescription
import nil.ed.easywork.generator.config.Config
import nil.ed.easywork.generator.context.GenerateContextBuilder
import nil.ed.easywork.source.obj.type.BaseClass

import java.util.stream.Collectors

class ConditionTemplateConfig extends AbstractProcessListFieldTemplateConfig {

    final String CONDITION_CXT = "CXT"

    @Override
    void buildCxt(Map<String, Object> context, String template, Config config) throws IOException {
        super.buildCxt(context, template, config)
        ConditionCxt cxt = new ConditionCxt()
        List<FieldValue> valueList = context.get(PROCESSED_LIST_FIELD) as List<FieldValue>
        List<CommentDescription> descs = context.get(GenerateContextBuilder.FIELD_DESC) as List<CommentDescription>
        cxt.hasAnyQuery = descs.stream().anyMatch(desc -> desc.query)
        cxt.hasAnyCollection = valueList.stream().anyMatch(v -> v.isCollectionSuffix)
        cxt.joinedCollectionStr = valueList.stream().filter(v -> v.isCollectionSuffix).map(v -> v.hasSuffixRealName).collect(Collectors.joining(", "));
        cxt.processedFields = valueList
        context.put(CONDITION_CXT, cxt)
    }

    @Override
    void afterAction(Map<String, Object> context, String template, Config config) throws IOException {
        super.afterAction(context, template, config)
        context.remove(CONDITION_CXT)
    }

    @Override
    String getFile(Map<String, Object> context, String template, BaseClass entity, Config config) {
        return "/condition/${entity.getName()}QueryCondition.java"
    }

    class ConditionCxt {
        boolean hasAnyCollection
        boolean hasAnyQuery
        String joinedCollectionStr
        List<AbstractProcessListFieldTemplateConfig.FieldValue> processedFields;
    }

}
