!=REPO
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">

import ${root.basePkg}.condition.${entity.name}QueryCondition;
import ${modelFullName};

import java.util.List;

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
/**
 * @author easywork.
 */
public interface ${entity.name}Repo {

    /**
     * 更新实体.
     * @param ${entityCamelName} 更新对象.
     * @return 影响行数.
     */
    long insert(${entity.name} ${entityCamelName});

    /**
     * 更新实体.
     * @param ${entityCamelName} 更新对象.
     * @return 影响行数.
     */
    long update(${entity.name} ${entityCamelName});

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    List<${entity.name}> getList(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    long count(${entity.name}QueryCondition condition);

    /**
     * 根据id查询记录.
     * @param condition 条件.
     * @return 结果.
     */
    ${entity.name} getOneById(Long id);

}
<#noparse></#noparse>