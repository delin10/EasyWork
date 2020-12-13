==>>/new_tpl/kyle/config/RepositoryInterfaceTemplateConfig.groovy
package ${root.basePkg}.repository;

__IMPORTS__
/**
 * @author easywork.
 */
public interface ${entity.name}Repo {

    /**
     * 更新${table.call}.
     * @param ${entityCamelName} 更新${table.call}.
     * @return 影响行数.
     */
    long insert(${entity.name} ${entityCamelName});

    /**
     * 更新${table.call}.
     * @param ${entityCamelName} 更新对象.
     * @return 影响行数.
     */
    long update(${entity.name} ${entityCamelName});

    /**
     * 根据条件查询${table.call}列表.
     * @param condition 条件.
     * @return ${table.call}.
     */
    List<${entity.name}> getList(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询${table.call}数量.
     * @param condition 条件.
     * @return ${table.call}.
     */
    long count(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询${table.call}.
     * @param condition 条件.
     * @return ${table.call}.
     */
    ${entity.name} getOne(${entity.name}QueryCondition condition);

}
<#noparse></#noparse>