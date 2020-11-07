==>>/new_tpl/kyle/config/EntityTemplateConfig.groovy
package ${root.basePkg}.entity;

<@JavaImportsIn value="entity.imports"/>
__IMPORTS__
/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class ${entity.name}Entity {

<#list entity.fields as field>
    /**
     * ${fieldColMap[field.name].comment}.
     */
    private ${field.type.name} ${field.name};

</#list>
}
<#noparse></#noparse>