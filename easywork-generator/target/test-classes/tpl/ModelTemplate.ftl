!=MODEL
package ${root.basePkg}.model;

<#list entity.imports as import>
import ${import};
</#list>

import lombok.Data
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ${entity.name} {

<#list entity.fields as field>
    private ${field.type.name} ${field.name};

</#list>
}