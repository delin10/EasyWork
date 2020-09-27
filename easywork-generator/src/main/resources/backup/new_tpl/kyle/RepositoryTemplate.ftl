!=REPO_IMPL
package ${root.basePkg}.repository.impl;

<#assign modelFullName="${root.basePkg}.model.${entity.name}">
import ${root.basePkg}.condition.${entity.name}QueryCondition;
import ${root.basePkg}.dao.${entity.name}Mapper;
import ${modelFullName};
import ${root.basePkg}.repository.${entity.name}Repo;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.annotation.Resource;

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
/**
 * @author easywork.
 */
@Repository
public class ${entity.name}RepositoryImpl implements ${entity.name}Repo {

    @Resource
    private ${entity.name}Mapper ${entityCamelName}Mapper;

    @Override
    public long insert(${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Mapper.insert(${entityCamelName});
    }

    @Override
    public long update(${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Mapper.update(${entityCamelName});
    }

    @Override
    public List<${entity.name}> getList(${entity.name}QueryCondition condition) {
        return ${entityCamelName}Mapper.getList(condition);
    }

    @Override
    public long count(${entity.name}QueryCondition condition) {
        return ${entityCamelName}Mapper.count(condition);
    }

    @Override
    public ${entity.name} getOneById(Long id) {
        return ${entityCamelName}Mapper.getOne(id);
    }


}
<#noparse></#noparse>