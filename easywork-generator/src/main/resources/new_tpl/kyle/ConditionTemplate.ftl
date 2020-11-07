==>>/new_tpl/kyle/config/ConditionTemplateConfig.groovy
package ${root.basePkg}.condition;

__IMPORTS__
/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class ${entity.name}QueryCondition extends PageCondition {

<#list CXT.processedFields as field>
    private ${field.realType} ${field.hasSuffixRealName};

</#list>
<#if CXT.hasAnyQuery>
    private String query;

</#if>
<#list CXT.processedFields as field>
    <#if field.isCollectionSuffix>
    public ${entity.name}QueryCondition set${field.noSuffixPascalName}(${field.listCollectionGenericType} ${field.noSuffixRealName}) {
        if (${field.noSuffixRealName} != null) {
            this.${field.hasSuffixRealName} = Collections.singletonList(${field.noSuffixRealName});
        }
        return this;
    }

    </#if>
</#list>
<#if CXT.hasAnyCollection>
    public boolean anySizeEmpty() {
        return CommonCollectionUtils.anySizeEmpty(${CXT.joinedCollectionStr});
    }

</#if>
}
<#noparse></#noparse>