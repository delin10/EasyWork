!=MAPPER
package ${root.basePkg}.dao;

<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
import org.apache.ibatis.annotations.Param;
import ${root.basePkg}.condition.${entity.name}QueryCondition;
import ${modelFullName};

import java.util.List;

/**
 * @author easywork.
 */
public interface ${entity.name}Mapper {

    Long insert(${entity.name} ${entityCamelName});

    Long update(${entity.name} ${entityCamelName});

    List<${entity.name}> list(${entity.name}QueryCondition condition);

    long count(${entity.name}QueryCondition condition);

}