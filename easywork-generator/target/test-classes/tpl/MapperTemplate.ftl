!=MAPPER
package ${root.basePkg};

import org.apache.ibatis.annotations.Param;

import java.util.List;
import ${entity.getFullyName()};

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

public interface ${entity.name}Mapper {

    Long insert(${entity.name} ${entityCamelName});

    Long update(${entity.name} ${entityCamelName});

    List<${entity.name}> list(<#list listFields as f>@Param(name = "${f.name}") ${f.type.name} ${f.name}, </#list>@Param("start") int start, @Param("size") int size);

    List<${entity.name}> search(@Param("query") String query, int start, int size);

}