package nil.ed.easywork.generator.type.impl;

import nil.ed.easywork.generator.type.ColTypeTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin.
 */
public class MyBatisColTypeTransformer implements ColTypeTransformer {

    private static final Map<String, String> TRANSFORM_MAP = new HashMap<>();
    static {
        TRANSFORM_MAP.put("TEXT", "VARCHAR");
        TRANSFORM_MAP.put("INT", "INTEGER");
    }

    @Override
    public String transform(String type) {
        return TRANSFORM_MAP.getOrDefault(type.toUpperCase(), type);
    }

}
