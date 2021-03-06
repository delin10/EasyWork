!=ENTITY
package ${root.basePkg}.entity;
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

<#list entity.imports as import>
import ${import};
</#list>
import ${root.basePkg}.model.${entity.name};
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

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