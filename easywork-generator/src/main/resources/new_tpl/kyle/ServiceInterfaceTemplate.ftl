==>>/new_tpl/kyle/config/ServiceInterfaceTemplateConfig.groovy
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

<@JavaImportIn value="${root.basePkg}.condition.${entity.name}QueryCondition"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.exception.BizException"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.page.PageResult"/>
<@JavaImportIn value="${root.basePkg}.entity.${entity.name}Entity"/>
<@JavaImportOut/>

/**
 * @author easywork.
 */
public interface ${entity.name}Service {

    /**
     * 更新实体.
     * @param ${entityCamelName}Entity 更新对象.
     * @throws BizException 插入失败.
     */
    void add${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException;

    /**
     * 更新实体.
     * @param ${entityCamelName}Entity 更新对象.
     * @throws BizException 更新失败.
     */
    void update${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException;

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    PageResult<${entity.name}Entity> get${entity.name}PageResult(${entity.name}QueryCondition condition);

   /**
    * 根据id查询记录.
    * @param condition 条件.
    * @return 结果.
    */
    ${entity.name} getById(Long id);

}
<#noparse></#noparse>