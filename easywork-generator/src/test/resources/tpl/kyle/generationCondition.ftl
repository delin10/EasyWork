!=COND
package ${root.basePkg}.condition;

import lombok.Data
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.List;
/**
 * @author easywork.
 */
@Data
@Accessors(chain = true)
public class ${entity.name}QueryCondition extends PageCondition {

<#list listFields as field>
    private ${fieldDescs[field+"-list"].type} ${field};

</#list>
<#if searchFields?size gt 0>
    private String query;
</#if>
}