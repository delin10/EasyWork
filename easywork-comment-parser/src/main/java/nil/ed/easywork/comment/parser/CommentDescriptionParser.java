package nil.ed.easywork.comment.parser;

import com.google.common.collect.Sets;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.comment.obj.EnumItemDesc;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注释解析器
 * 语法： {{func[func1(identifier),func2(identifier)]&func[]}}
 * list[name(alias),type(String)]
 * enum[name(),enums(<ID0:id@desc><ID2:id2@desc>)]
 * @author delin10
 * @since 2020/5/28
 *
 * fixme 使用antrr来设计解析comment
 **/
public class CommentDescriptionParser {

    private static final Map<FunctionEnum, Map<String, Function<String, Object>>> MAPPER = new HashMap<>();
    private static final Function<String, Object> DEFAULT_MAPPER = v -> v;

    private final Character terminateChar = ']';
    private final Set<Character> startCharSet = Sets.newHashSet('[', '(', ',');
    private final Set<Character> endCharSet = Sets.newHashSet(']', ')');

    static {
        registerMapper(FunctionEnum.ENUM, "enums", CommentDescriptionParser::resolveEnums);
    }

    public static void registerMapper(FunctionEnum func, String name, Function<String, Object> mapper) {
        Map<String, Function<String, Object>> map = MAPPER.computeIfAbsent(func, k -> new HashMap<>());
        map.put(name, mapper);

    }

    public List<CommentDescription> parse(String comment) {
        if (StringUtils.isBlank(comment)) {
            return Collections.emptyList();
        }
        int i = 0;
        String[] arr = comment.split("&");
        List<CommentDescription> commentDescriptions = new LinkedList<>();
        for (String one : arr) {
            System.out.println(one);
            CommentDescription description = new CommentDescription();
            parseOneDescription(one, description, 0);
            commentDescriptions.add(description);
        }
        return commentDescriptions;
    }

    private void parseOneDescription(String str, CommentDescription description, int i) {
        if (StringUtils.isBlank(str)) {
            return;
        }
        String identifier = getOneIdentifier(str, i, startCharSet);
        description.setFunc(FunctionEnum.findByName(identifier));
        i += identifier.length() + 1;
        while (i < str.length() && str.charAt(i) != terminateChar) {
            String name = getOneIdentifier(str, i, startCharSet).trim();
            if (StringUtils.isBlank(name)) {
                i++;
                continue;
            }
            i += name.length() + 1;
            String value = getOneIdentifier(str, i, endCharSet).trim();
            if (StringUtils.isBlank(value)) {
                i++;
                continue;
            }
            Object finalValue = resolveValue(description.getFunc(), name, value);
            i += value.length() + 1;
            set(description, name, finalValue);
        }
    }

    private String getOneIdentifier(String str, int cur, Set<Character> utilChar) {
        int start = cur;
        while (cur < str.length() && !utilChar.contains(str.charAt(cur++))) { }
        return str.substring(start, cur == str.length() ? cur : cur - 1);
    }

    private void set(Object bean, String name, Object value) {
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object resolveValue(FunctionEnum func, String name, String v) {
        return MAPPER.getOrDefault(func, Collections.emptyMap())
                .getOrDefault(name, DEFAULT_MAPPER).apply(v);
    }

    private static final Pattern ENUM_PATTERN = Pattern.compile("<(.*?)>");
    private static List<EnumItemDesc> resolveEnums(String str) {
        Matcher matcher = ENUM_PATTERN.matcher(str);
        List<EnumItemDesc> result = new LinkedList<>();
        while (matcher.find()) {
            String one = matcher.group(1);
            String[] pair1 = one.split("@");
            String[] pair2 = pair1[0].split(":");
            EnumItemDesc desc = new EnumItemDesc();
            desc.setName(pair2[0]);
            desc.setId(pair2[1]);
            desc.setDesc(pair1[1]);
            result.add(desc);
        }
        return result;
    }

}
