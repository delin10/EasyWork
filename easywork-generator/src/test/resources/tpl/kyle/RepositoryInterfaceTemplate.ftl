!=REPO
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">

import ${modelFullName};
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
public interface ${entity.name}Repo {

    @Resource
    private ${entity.name}Mapper ${entityCamelName}Mapper;

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
    List<${entity.name}> list(${entity.name}QueryCondition condition);

    /**
     * 根据条件查询列表.
     * @param condition 条件.
     * @return 结果.
     */
    long count(${entity.name}QueryCondition condition);

}