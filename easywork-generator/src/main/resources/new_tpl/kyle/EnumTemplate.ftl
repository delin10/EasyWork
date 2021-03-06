==>>/new_tpl/kyle/config/EnumTemplateConfig.groovy
package ${root.basePkg}.enums;

__IMPORTS__
/**
* @author easywork.
*/
@Getter
@AllArgsConstructor
public enum ${currentEnum.name} implements EnumBase {
    <#list currentEnum.enums as e>
    ${e.name}(${e.id}, "${e.desc}"),
    </#list>
    ;

    private static final Map<Integer, ${currentEnum.name}> MAP;

    private final int code;

    private final String desc;

    static {
        MAP = EnumUtils.asUnmodifiableMap(${currentEnum.name}.class);
    }

    public static ${currentEnum.name} findByCode(Integer code) {
        return MAP.get(code);
    }

}
<#noparse></#noparse>