package nil.ed.easywork.generator.context;

import nil.ed.easywork.generator.tools.Tool;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public class GenerateContextBuilder {

    public static Map<String, Object> utils = new HashMap<>();

    public static final String UNDER_LINE_TO_CAMEL = "underlineToCamel";
    public static final String CAMEL_TO_UNDER_LINE = "camelToUnderline";
    public static final String PASCAL_TO_CAMEL = "pascalToCamel";
    public static final String ENTITY = "entity";
    public static final String TABLE = "table";
    public static final String FUNC_FIELDS_SUFFIX = "Fields";
    public static final String FIELD_DESC = "fieldDescs";
    public static final String FIELD_COL_MAP = "fieldColMap";
    public static final String COL_FIELD_MAP = "colFieldMap";
    public static final String UTILS = "utils";
    public static final String ROOT = "root";
    public static final String ADDITIONAL = "additional";
    public static final String CURRENT_IMPORTS = "imports";
    public static final String ADDITIONAL_PATTERN_IMPORT = "additionalPatternImport";
    public static final String CURRENT_RENDER_CLAZZ_NAME = "currentRenderClazzName";
    public static final String MULTI_SEPARATOR = ">>>>>>>>>>";

    private Map<String, Object> context = new HashMap<>();

    private List<Tool> toolList = new LinkedList<>();


    static {
        utils.put(UNDER_LINE_TO_CAMEL, NamingTranslatorSingleton.UNDERLINE_TO_CAMEL);
        utils.put(PASCAL_TO_CAMEL, NamingTranslatorSingleton.PASCAL_TO_CAMEL);
        utils.put(CAMEL_TO_UNDER_LINE, NamingTranslatorSingleton.CAMEL_TO_UNDERLINE);
    }

    public GenerateContextBuilder set(String name, Object obj) {
        context.put(name, obj);
        return this;
    }

    @SuppressWarnings("unchecked")
    public GenerateContextBuilder addAdditional(String name, Object obj) {
        ((Map<String,Object>)context.computeIfAbsent(name, k -> new HashMap<>())).put(name, obj);
        return this;
    }

    public GenerateContextBuilder registerTool(Tool...tools) {
        Collections.addAll(toolList, tools);
        return this;
    }

    public Map<String, Object> build() {
        context.put(UTILS, utils);
        toolList.forEach(tool -> {
            utils.put(tool.getName(), tool);
        });
        return context;
    }

}
