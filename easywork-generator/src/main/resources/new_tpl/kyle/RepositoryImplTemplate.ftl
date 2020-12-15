==>>/new_tpl/kyle/config/RepositoryImplTemplateConfig.groovy
package ${root.basePkg}.repository.impl;

__IMPORTS__
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
        <#if CXT.hasAnyCollection>
        if (condition.isEmptyCondition()) {
            return Collections.emptyList();
        }

        </#if>
        return ${entityCamelName}Mapper.getList(condition);
    }

    @Override
    public long count(${entity.name}QueryCondition condition) {
        <#if CXT.hasAnyCollection>
        if (condition.isEmptyCondition()) {
            return 0;
        }

        </#if>
        return ${entityCamelName}Mapper.count(condition);
    }

    @Override
    public ${entity.name} getOne(${entity.name}QueryCondition condition) {
        return ${entityCamelName}Mapper.getOne(condition);
    }


}
<#noparse></#noparse>