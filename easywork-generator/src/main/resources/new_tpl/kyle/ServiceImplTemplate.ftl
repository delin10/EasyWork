==>>/new_tpl/kyle/config/ServiceImplTemplateConfig.groovy
package ${root.basePkg}.repository;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">

<@JavaImportIn value="${root.basePkg}.entity.${entity.name}Entity"/>
<@JavaImportIn value="${root.basePkg}.exception.${entity.name}BizErrorType"/>
<@JavaImportIn value="${root.basePkg}.repository.${entity.name}Repo"/>
<@JavaImportIn value="${root.basePkg}.service.${entity.name}Service"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.exception.BizException"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.page.PageResult"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.utils.PageUtils"/>
<@JavaImportIn value="lombok.extern.slf4j.Slf4j"/>
<@JavaImportIn value="org.springframework.stereotype.Service"/>
<@JavaImportIn value="javax.annotation.Resource"/>
<@JavaImportOut/>

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
        long ret = ${entityCamelName}Repo.insert(${entity.name}Converter.to${entity.name}(${entity.name}Entity));
        if (ret <= 0) {
            log.error("Failed to insert {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.INSERT_FAILED);
        }
    }

    @Override
    public void update${entity.name}(${entity.name}Entity ${entityCamelName}Entity)  throws BizException {
        long ret = ${entityCamelName}Repo.update(${entity.name}Converter.to${entity.name}(${entity.name}Entity));
        if (ret <= 0) {
            log.error("Failed to update {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.UPDATE_FAILED);
        }
    }

    @Override
    public PageResult<${entity.name}Entity> get${entity.name}PageResult(${entity.name}QueryCondition condition) {
        log.debug("Start to invoke get${entity.name}PageResult with params: condition = {}", condition);
        PageResult<${entity.name}Entity> result = PageUtils.selectPageLong(condition, ${entityCamelName}Repo::getList,
            ${entityCamelName}Repo::count, ${entity.name}Converter::to${entity.name}Entity);
        log.debug("Succeed to invoke get${entity.name}PageResult with result: {}", result);
        return result;
    }

    @Override
    public ${entity.name}Entity getById(Long id) {
        log.debug("Start to invoke getById with params: id = {}", id);
        ${entity.name} result = ${entityCamelName}Repo.getOne(id);
        log.debug("Succeed to invoke getById with result: {}", result);
        return {entity.name}Converter.to${entity.name}Entity(result);
    }

}
<#noparse></#noparse>