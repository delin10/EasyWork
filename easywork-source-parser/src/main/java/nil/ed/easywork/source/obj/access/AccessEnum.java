package nil.ed.easywork.source.obj.access;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@AllArgsConstructor
public enum AccessEnum {
    /**
     * 访问级别
     */
    PRIVATE("private"),
    PROTECTED("protected"),
    PACKAGE(""),
    PUBLIC("public");
    private String name;

}
