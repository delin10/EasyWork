==>>/new_tpl/kyle/config/MapperTemplateConfig.groovy
package ${root.basePkg}.dao;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

__IMPORTS__
/**
 * @author easywork.
 */
public interface ${entity.name}Mapper {

    /**
     * 插入记录.
     * @param ${entityCamelName} 记录.
     * @return 影响行数.
     */
    int insert(${entity.name} ${entityCamelName});

    /**
     * 更新记录.
     * @param ${entityCamelName} 记录.
     * @return 影响行数.
     */
    int update(${entity.name} ${entityCamelName});

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 列表.
     */
    List<${entity.name}> getList(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询记录总数.
     * @param condition 条件.
     * @return 总数.
     */
    int count(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询单条记录.
     * @param condition 条件.
     * @return 对象.
     **/
    ${entity.name} getOne(${entity.name}QueryCondition condition);

}
<#noparse></#noparse>