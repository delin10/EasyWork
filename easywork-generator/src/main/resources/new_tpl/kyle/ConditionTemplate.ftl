==>>/new_tpl/kyle/config/ConditionTemplateConfig.groovy
package ${root.basePkg}.condition;
<#function isCollection name>
    <#return name?ends_with("Set") || name?ends_with("List") || name?ends_with("Collection")>
</#function>
<#function removeCollectionFlag name>
    <#return name?replace("Set", "")?replace("List", "")?replace("Collection", "")>
</#function>
<#function getGeneric name>
    <#return utils.TYPE_TOOL.getGenericType(name)>
</#function>

<@JavaImportIn value="com.kuaikan.ads.kyle.common.page.PageCondition"/>
<@JavaImportIn value="lombok.Data"/>
<@JavaImportIn value="lombok.experimental.Accessors"/>
<@JavaImportIn value="java.util.Set"/>
<@JavaImportIn value="java.util.List"/>
<@JavaImportIn value="java.util.Collections"/>
<@JavaImportOut/>

/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class ${entity.name}QueryCondition extends PageCondition {

<#list listFields as field>
    private ${fieldDescs[field+"-list"].type} ${fieldDescs[field+"-list"].name};

</#list>
<#if searchFields?size gt 0>
    private String query;

</#if>
<#list listFields as field>
    <#if isCollection(fieldDescs[field+"-list"].name)>
    public ${entity.name}QueryCondition set${utils.removeCollectionFlag(fieldDescs[field+"-list"].name)?capitalize}(${getGeneric(fieldDescs[field+"-list"].type)} ${removeCollectionFlag(fieldDescs[field+"-list"].name)}) {
        if (${removeCollectionFlag(fieldDescs[field+"-list"].name)} == null) {
            this.${fieldDescs[field+"-list"].name} = null;
        } else {
            this.${fieldDescs[field+"-list"].name} = Collections.singletonList(${removeCollectionFlag(fieldDescs[field+"-list"].name)});
        }
        return this;
    }

    </#if>
</#list>
}
<#noparse></#noparse>