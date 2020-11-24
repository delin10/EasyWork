==>>/wiki/config/VoTemplateConfig.groovy
package ${basePackage}.vo;

__IMPORTS__
@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${current.id} {
<#list current.params as field>
    /**
     * ${field.comment}.
     */
    private ${field.type} ${field.name};

</#list>
}