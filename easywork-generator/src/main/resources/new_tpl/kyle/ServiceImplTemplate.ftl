==>>/new_tpl/kyle/config/ServiceImplTemplateConfig.groovy
package ${root.basePkg}.repository;

__IMPORTS__
/**
 * @author easywork.
 */
@Slf4j
@Service
public class ${entity.name}ServiceImpl implements ${entity.name}Service {

    @Resource
    private ${entity.name}Repo ${entityCamelName}Repo;

    @Override
    public ${entity.name}Entity add${entity.name}(${entity.name}Entity ${entityCamelName}Entity) throws BizException {
        ${entity.name} ${entityCamelName} = ${entity.name}Converter.to${entity.name}(${entityCamelName}Entity);
        int ret = ${entityCamelName}Repo.insert();
        if (ret <= 0) {
            log.error("Failed to insert {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.SYSTEM_ERR);
        }
        ${entityCamelName}Entity.setId(${entityCamelName}.getId());
        return ${entityCamelName}Entity;
    }

    @Override
    public void update${entity.name}(${entity.name}Entity ${entityCamelName}Entity)  throws BizException {
        int ret = ${entityCamelName}Repo.update(${entity.name}Converter.to${entity.name}(${entityCamelName}Entity));
        if (ret <= 0) {
            log.error("Failed to update {}", ${entityCamelName}Entity);
            throw new BizException(${entity.name}BizErrorType.SYSTEM_ERR);
        }
    }

    @Override
    public PageResult<${entity.name}Entity> get${entity.name}PageResult(${entity.name}QueryCondition condition) {
        log.debug("Start to invoke get${entity.name}PageResult with params: condition = {}", condition);
        PageResult<${entity.name}Entity> result = PageUtils.selectPageLong(condition, ${entityCamelName}Repo::getList,
            ${entityCamelName}Repo::count, ${entity.name}Converter::to${entity.name}Entity);
        log.debug("Succeed to invoke get${entity.name}PageResult with result.size: {}", result.getList().size());
        return result;
    }

    @Override
    public List<${entity.name}Entity> get${entity.name}List(${entity.name}QueryCondition condition) {
        log.debug("Start to invoke get${entity.name}List with params: condition = {}", condition);
        List<${entity.name}Entity> result = ListUtils.transformList(${entityCamelName}Repo.getList(condition),
            ${entity.name}Converter::to${entity.name}Entity);
        log.debug("Succeed to invoke get${entity.name}List with result.size: {}", result.size());
        return result;
    }

    @Override
    public ${entity.name}Entity get${entity.name}(Long id) {
        log.debug("Start to invoke getById with params: id = {}", id);
        ${entity.name} result = ${entity.name}Converter.to${entity.name}Entity(${entityCamelName}Repo.getOne(id));
        log.debug("Succeed to invoke getById with result: {}", result);
        return ${entity.name}Converter.to${entity.name}Entity(result);
    }

}
<#noparse></#noparse>