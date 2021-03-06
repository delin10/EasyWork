package nil.ed.easywork.comment.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/5/28
 **/
@Getter
@AllArgsConstructor
public enum FunctionEnum {
    /**
     * 注释函数枚举
     */
    @Deprecated
    INSERT("insert"),
    LIST("list"),
    @Deprecated
    SEARCH("search"),
    UPDATE("update"),
    @Deprecated
    VO("vo"),
    ENUM("enum");
    private final String name;

    private static final Map<String, FunctionEnum> map = new HashMap<>(8, 1);

    static {
        Arrays.stream(FunctionEnum.values())
                .forEach(e -> {
                    map.put(e.name, e);
                });
    }

    public static FunctionEnum findByName(String name) {
        return map.get(name.toLowerCase());
    }

}
