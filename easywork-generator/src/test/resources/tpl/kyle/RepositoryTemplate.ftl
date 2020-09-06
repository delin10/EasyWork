!=REPO_IMPL
package ${root.basePkg}.repository.impl;

<#assign modelFullName="${root.basePkg}.model.${entity.name}">
import ${modelFullName};
import ${root.basePkg}.repository.${entity.name}Repo
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.util.List;

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
/**
 * @author easywork.
 */
@SLF4J
@Service
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
    public List<${entity.name}> list(${entity.name}QueryCondition condition) {
        ${entityCamelName}Mapper.list(condition);
    }

    public long count(${entity.name}QueryCondition condition) {
        ${entityCamelName}Mapper.list(condition);
    }

}