!=SERVICE
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">

import ${root.basePkg}.condition.${entity.name}QueryCondition;
import com.kuaikan.ads.kyle.common.exception.BizException;
import com.kuaikan.ads.kyle.common.page.PageResult;
import ${root.basePkg}.entity.${entity.name}Entity;

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
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