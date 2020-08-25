!=SERVICE
package ${root.basePkg}.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.util.List;
import ${entity.getFullyName()};

<#assign paramStr><#if listFields?size != 0><#list listFields as f>${f.type.name} ${f.name}<#sep>, </#sep></#list></#if></#assign>
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
@SLF4J
@Service
public interface ${entity.name}Service {

    @Resource
    private ${entity.name}Mapper ${entityCamelName}Mapper;

    public Response insert(${entity.name} ${entityCamelName}) {
        log.info("Start to invoke insert with params {}", ${entityCamelName});
        try {
            int ret = ${entityCamelName}Mapper.insert(${entityCamelName});
            if (ret > 0) {
                return ResponseUtils.success();
                log.info("Succeed to invoke insert！");
            }
        } catch (Exception e) {
            log.error("Failed to invoke insert with Exception!", e);
        }
        return ResponseUtils.fail();
    }

    public Response update(${entity.name} ${entityCamelName}) {
        log.info("Start to invoke update with params {}", ${entityCamelName});
        try {
            int ret = ${entityCamelName}Mapper.update(${entityCamelName});
            if (ret > 0) {
                return ResponseUtils.success();
                log.info("Succeed to invoke update！");
            }
        } catch (Exception e) {
            log.error("Failed to invoke update with Exception!", e);
        }
        return ResponseUtils.fail();
    }

    public Response getById(@Param("id") Long id) {
        log.info("Start to invoke getById with params id = {}", id);
        try {
            ${entity.name} result = ${entityCamelName}Mapper.getById(id);
            log.info("Succeed to invoke getById with result = {}！", result);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            log.error("Failed to invoke getById with Exception!", e);
        }
        return ResponseUtils.fail();
    }

    public Response list(<#if ((paramStr!"")?trim?length) != 0>${paramStr}, </#if>int pageNo, int size) {
        log.info("Start to invoke list with params id = {}", ${paramStr});
        try {
            int start = pageNo * size;
            List<${entity.name}> result = ${entityCamelName}Mapper.list(<#if ((paramStr!"")?trim?length) != 0>${paramStr}, </#if>start, size);
            log.info("Succeed to invoke list with result = {}！", result);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            log.error("Failed to invoke list with Exception!", e);
        }
        return ResponseUtils.fail();
    }


    public List<${entity.name}> search(@Param("query") String query,
                                Long start,
                                Long size) {
        log.info("Start to invoke search with params query = {}, start = {}, size = {}", query, start, size);
        try {
            List<${entity.name}> result = ${entityCamelName}Mapper.search(id);
            log.info("Succeed to invoke search with result = {}！", result);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            log.error("Failed to invoke search with Exception!", e);
        }
        return ResponseUtils.fail();
    }

}