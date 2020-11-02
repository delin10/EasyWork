==>>/new_tpl/kyle/config/ConditionTemplateConfig.groovy
package ${root.basePkg}.condition;
<#function isCollection name>
    <#return name?ends_with("Set") || name?ends_with("List") || name?ends_with("Collection")>
</#function>
<#function hasAnyCollection fields>
    <#list listFields as field>
        <#if isCollection(fieldDescs[field+"-list"].name)>
            <#return true>
        </#if>
    </#list>
    <#return false>
</#function>

<@JavaImportIn value="com.kuaikan.ads.kyle.common.page.PageCondition"/>
<#if hasAnyCollection(listFields)><@JavaImportIn value="com.kuaikan.ads.kyle.common.utils.CommonCollectionUtils"/></#if>
<@JavaImportIn value="lombok.Data"/>
<@JavaImportIn value="lombok.experimental.Accessors"/>
<@JavaImportIn value="java.util.Collection"/>
<@JavaImportIn value="java.util.Collections"/>
<@JavaImportOut/>

/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class ${entity.name}QueryCondition extends PageCondition {

<#list processedListFields as field>
    private ${field.type} ${field.realName};

</#list>
<#if searchFields?size gt 0>
    private String query;

</#if>
<#list processedListFields as field>
    <#if field.isCollectionSuffix>
    public ${entity.name}QueryCondition set${field.pascalName}(${field.generic} ${field.cutRealName}) {
        if (${field.cutRealName} != null) {
            this.${field.realName} = Collections.singletonList(${field.cutRealName});
        }
        return this;
    }

    </#if>
</#list>
<#if hasAnyCollection(listFields)>
    public boolean anySizeEmpty() {
        return CommonCollectionUtils.anySizeEmpty(<#list listFields as field><#if isCollection(fieldDescs[field+"-list"].name)>condition.get${utils.pascalToCamel.trans(fieldDescs[field+"-list"].name)}()<#sep>, </#sep></#if></#list>));
    }

</#if>
}
<#noparse></#noparse>