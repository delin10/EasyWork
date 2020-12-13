==>>/new_tpl/kyle/config/ServiceInterfaceTemplateConfig.groovy
package ${root.basePkg}.repository;

__IMPORTS__
/**
 * @author easywork.
 */
public interface ${entity.name}Service {

    /**
     * 新增${table.call}.
     * @param ${entityCamelName}Entity ${table.call}.
     * @throws BizException 插入失败.
     */
    ${entity.name}Entity add${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException;

    /**
     * 更新${table.call}.
     * @param ${entityCamelName}Entity 更新${table.call}.
     * @throws BizException 更新失败.
     */
    void update${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException;

    /**
     * 根据条件查询${table.call}分页列表.
     * @param condition 条件.
     * @return ${table.call}分页列表.
     */
    PageResult<${entity.name}Entity> get${entity.name}PageResult(${entity.name}QueryCondition condition);

    /**
    * 根据条件查询${table.call}列表.
    * @param condition 条件.
    * @return ${table.call}列表.
    */
    List<${entity.name}Entity> get${entity.name}List(${entity.name}QueryCondition condition);

   /**
    * 根据条件查询${table.call}记录.
    * @param condition 条件.
    * @return ${table.call}.
    */
    ${entity.name}Entity get${entity.name}(${entity.name}QueryCondition condition);

}
<#noparse></#noparse>