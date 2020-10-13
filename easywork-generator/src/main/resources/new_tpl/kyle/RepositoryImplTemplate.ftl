==>>/new_tpl/kyle/config/RepositoryImplTemplateConfig.groovy
package ${root.basePkg}.repository.impl;
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#assign entityCamelName="${utils.pascalToCamel.trans(entity.name)}">
<#function isCollection name>
    <#return name?ends_with("Set") || name?ends_with("List") || name?ends_with("Collection")>
</#function>
<#function hasAnyCollection fields>
    <#list listFields as field>
        <#if isCollection(fieldDescs[field+"-list"].name)>
            <#return true>
        </#if>
    </#list>
    <#return false>
</#function>

<@JavaImportIn value="${root.basePkg}.condition.${entity.name}QueryCondition"/>
<@JavaImportIn value="${root.basePkg}.dao.${entity.name}Mapper"/>
<@JavaImportIn value="${modelFullName}"/>
<@JavaImportIn value="${root.basePkg}.repository.${entity.name}Repo"/>
<@JavaImportIn value="org.springframework.stereotype.Repository"/>
<@JavaImportIn value="java.util.List"/>
<@JavaImportIn value="javax.annotation.Resource"/>
<@JavaImportIn value="com.kuaikan.ads.kyle.common.utils.CommonCollectionUtils"/>
<@JavaImportOut/>

/**
 * @author easywork.
 */
@Repository
public class ${entity.name}RepositoryImpl implements ${entity.name}Repo {

    @Resource
    private ${entity.name}Mapper ${entityCamelName}Mapper;

    @Override
    public long insert(${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Mapper.insert(${entityCamelName});
    }

    @Override
    public long update(${entity.name} ${entityCamelName}) {
        return ${entityCamelName}Mapper.update(${entityCamelName});
    }

    @Override
    public List<${entity.name}> getList(${entity.name}QueryCondition condition) {
        <#if hasAnyCollection(listFields)>
        if (CommonCollectionUtils.anySizeEmpty(<#list listFields as field><#if isCollection(fieldDescs[field+"-list"].name)>condition.get${fieldDescs[field+"-list"].name?capitalize}()<#sep>, </#sep></#if></#list>)) {
            return Collections.emptyList();
        }

        </#if>
        return ${entityCamelName}Mapper.getList(condition);
    }

    @Override
    public long count(${entity.name}QueryCondition condition) {
        <#if hasAnyCollection(listFields)>
        if (condition.anySizeEmpty()) {
            return Collections.emptyList();
        }

        </#if>
        return ${entityCamelName}Mapper.count(condition);
    }

    @Override
    public ${entity.name} getOne(${entity.name}QueryCondition condition) {
        return ${entityCamelName}Mapper.getOne(id);
    }


}
<#noparse></#noparse>