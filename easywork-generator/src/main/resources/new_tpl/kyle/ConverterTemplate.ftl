==>>/new_tpl/kyle/config/ConverterTemplateConfig.groovy
package ${root.basePkg}.converter;
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

<@JavaImportIn value="${root.basePkg}.entity.${entity.name}Entity"/>
<@JavaImportIn value="${root.basePkg}.model.${entity.name}"/>
<@JavaImportIn value="org.springframework.beans.BeanUtils"/>
<@JavaImportOut/>

/**
 * @author easywork.
 */
public class ${entity.name}Converter {

    public ${entity.name} to${entity.name}(${entity.name}Entity entity) {
        ${entity.name} ${entityCamelName} = new ${entity.name}();
        BeanUtils.copyProperties(entity, ${entityCamelName});
        return ${entityCamelName};
    }

    public static ${entity.name}Entity to${entity.name}Entity(${entity.name} model) {
        if (model == null) {
            return null;
        }
        ${entity.name}Entity ${entityCamelName}Entity = new ${entity.name}Entity();
        BeanUtils.copyProperties(model, ${entityCamelName}Entity);
        return ${entityCamelName}Entity;
    }

}
<#noparse></#noparse>