!=SERVICE_IMPL
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">

import ${root.basePkg}.condition.${entity.name}QueryCondition;
import ${root.basePkg}.entity.${entity.name}Entity;
import ${root.basePkg}.exception.${entity.name}BizErrorType;
import ${root.basePkg}.repository.${entity.name}Repo;
import ${root.basePkg}.service.${entity.name}Service;
import com.kuaikan.ads.kyle.common.exception.BizException;
import com.kuaikan.ads.kyle.common.page.PageResult;
import com.kuaikan.ads.kyle.common.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
/**
 * @author easywork.
 */
@Slf4j
@Service
public class ${entity.name}ServiceImpl implements ${entity.name}Service {

    @Resource
    private ${entity.name}Repo ${entityCamelName}Repo;

    @Override
    public void add${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException {
        long ret = ${entityCamelName}Repo.insert(${entityCamelName}Entity.to${entity.name}());
        if (ret <= 0) {
            log.error("Failed to insert {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.INSERT_FAILED);
        }
    }

    @Override
    public void update${entity.name}(${entity.name}Entity ${entityCamelName}Entity)  throws BizException {
        long ret = ${entityCamelName}Repo.update(${entityCamelName}Entity.to${entity.name}());
        if (ret <= 0) {
            log.error("Failed to update {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.UPDATE_FAILED);
        }
    }

    @Override
    public PageResult<${entity.name}Entity> get${entity.name}PageResult(${entity.name}QueryCondition condition) {
        log.debug("Start to invoke get${entity.name}PageResult with params: condition = {}", condition);
        PageResult<${entity.name}Entity> result = PageUtils.selectPageLong(condition, ${entityCamelName}Repo::getList,
            ${entityCamelName}Repo::count, ${entity.name}Entity::parse);
        log.debug("Succeed to invoke get${entity.name}PageResult with result: {}", result);
        return result;
    }

    @Override
    public ${entity.name} getById(Long id) {
        log.debug("Start to invoke getById with params: id = {}", id);
        ${entity.name} result = ${entityCamelName}Repo.getOne(id);
        log.debug("Succeed to invoke getById with result: {}", result);
        return result;
    }

}
<#noparse></#noparse>