!=ENTITY
package ${root.basePkg}.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
<#list entity.imports as import>
import ${import};
</#list>

@Getter
@Setter
@ToString
public class ${entity.name} {

<#list entity.fields as field>
    <#if field.primary>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    private ${field.type.name} ${field.name};

</#list>

}