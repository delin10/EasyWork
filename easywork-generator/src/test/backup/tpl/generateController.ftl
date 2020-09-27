package ${root.basePkg}.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import ${root.basePkg}.service.${entity.name}Service;
import ${entity.getFullyName()};

<#assign paramStr><#list listFields as f>${f.type.name} ${f.name}<#sep>, </#sep></#list></#assign>
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
@RestController
@RequestMapping("/${entityCamelName}")
public interface ${entity.name}Controller {

    @Resource
    private ${entity.name}Service ${entityCamelName}Service;

    @PostMapping("/add")
    public Response add(@RequestBody ${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Service.insert(${entityCamelName});
    }

    @PostMapping("/update")
    public Response update(${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Service.insert(${entityCamelName});
    }

    @GetMapping("/get")
    public Response getById(@RequestParam("id") Long id) {
        return ${entityCamelName}Service.getById(id);
    }

    @GetMapping("/list")
    public Response list(<#if ((paramStr!"")?trim?length) != 0><#list listFields as f>@RequestParam(name = "${utils.camelToUnderline.trans(f.name)}") ${f.type.name} ${f.name}<#sep>, </#sep></#list>, </#if>@RequestParam("page_no") int pageNo, @RequestParam("size") int size) {
        if (pageNo < 0 || size <= 0) {
            return ResponseUtils.fail();
        }

        return ${entityCamelName}Service.list(<#if ((paramStr!"")?trim?length) != 0>${paramStr}, </#if>pageNo, size);
    }

    @GetMapping("/search")
    public List<${entity.name}> search(@RequestParam("query") String query, @RequestParam("page_num") Long pageNum, @RequestParam("size") Long size) {
        return ${entityCamelName}Service.list(query, pageNo, size);
    }

}