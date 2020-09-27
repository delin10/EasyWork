==>>/new_tpl/kyle/config/EntityTemplateConfig.groovy
package ${root.basePkg}.entity;
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

<@JavaImportsIn value="entity.imports"/>
<@JavaImportIn value="${root.basePkg}.model.${entity.name}"/>
<@JavaImportIn value="lombok.Data"/>
<@JavaImportIn value="lombok.experimental.Accessors"/>
<@JavaImportIn value="org.springframework.beans.BeanUtils"/>
<@JavaImportOut/>

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
    public ${entity.name} to${entity.name}() {
        ${entity.name} ${entityCamelName} = new ${entity.name}();
        BeanUtils.copyProperties(this, ${entityCamelName});
        return ${entityCamelName};
    }

    public static ${entity.name}Entity parse(${entity.name} model) {
        ${entity.name}Entity ${entityCamelName}Entity = new ${entity.name}Entity();
        BeanUtils.copyProperties(model, ${entityCamelName}Entity);
        return ${entityCamelName}Entity;
    }

}
<#noparse></#noparse>