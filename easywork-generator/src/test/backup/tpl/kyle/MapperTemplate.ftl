!=MAPPER
package ${root.basePkg}.dao;

<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
import org.apache.ibatis.annotations.Param;
import ${root.basePkg}.condition.${entity.name}QueryCondition;
import ${modelFullName};

import java.util.List;

/**
 * @author easywork.
 */
public interface ${entity.name}Mapper {

    /**
     * 插入记录.
     * @param ${entityCamelName} 记录.
     * @return 影响行数.
     */
    long insert(${entity.name} ${entityCamelName});

    /**
     * 更新记录.
     * @param ${entityCamelName} 记录.
     * @return 影响行数.
     */
    long update(${entity.name} ${entityCamelName});

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
    long count(${entity.name}QueryCondition condition);

    /**
     * 根据id查询.
     * @param id id.
     * @return 对象.
     **/
    ${entity.name} getOne(Long id);

}
<#noparse></#noparse>