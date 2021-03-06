==>>/new_tpl/kyle/config/ModelTemplateConfig.groovy
package ${root.basePkg}.model;

<@JavaImportsIn value="entity.imports"/>
__IMPORTS__
/**
* @author easywork.
*/
@Data
@Accessors(chain = true)
public class ${entity.name} {

<#list entity.fields as field>
    /**
     * ${fieldColMap[field.name].comment}.
     */
    private ${field.type.name} ${field.name};

</#list>
}
<#noparse></#noparse>