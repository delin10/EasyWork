==>>/wiki/config/ControllerTemplateConfig.groovy
package ${current.basePackage}.controller;

__IMPORTS__
@RestController
@RequestMapping("${current.path}")
public class ${current.id} {
    <#list current.apis as api>
    <#if api.method?? && api.method?lower_case = "post">@PostMapping<#else>@GetMapping</#if>("${api.path}")
    public Object ${api.id}(${tmp[api.id+"-sign"]}) {
        return ResponseUtils.success();
    }
    </#list>
}