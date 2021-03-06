==>>/new_tpl/kyle/config/ConverterTemplateConfig.groovy
package ${root.basePkg}.converter;

__IMPORTS__
/**
 * @author easywork.
 */
public class ${entity.name}Converter {

    public static ${entity.name} to${entity.name}(${entity.name}Entity entity) {
        if (entity == null) {
            return null;
        }
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