package nil.ed.easywork.source.obj.type.support;

import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.util.FlowUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class TypeSupport {

    private static final Pattern GENERIC_PARAM_PATTERN = Pattern.compile(".*?<(.*?)>");
    private static final Pattern TYPE_PATTERN = Pattern.compile("(.*?)(<(.*?)>)?");

    private static final String SPLIT_PATTERN = "\\s*,\\s*";

    public static JavaType resolveJavaType(String type) {
        return FlowUtils.continueIfNotNull(type)
                .filter(StringUtils::isNotBlank)
                .map(typeStr -> {
                    JavaType realType = null;
                    Matcher matcher = TYPE_PATTERN.matcher(type);
                    if (matcher.matches()) {
                        realType = JavaType.create(matcher.group(1));
                    }
                    FlowUtils.continueIfNotNull(realType)
                            .ifPresent(r -> {
                                Matcher genericParamMatcher = GENERIC_PARAM_PATTERN.matcher(type);
                                if (genericParamMatcher.matches()) {
                                    String genericParamStr = genericParamMatcher.group(1);
                                    String[] generics = genericParamStr.split(SPLIT_PATTERN);
                                    List<JavaType> gTypes = Stream.of(generics)
                                            .map(TypeSupport::resolveJavaType)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList());
                                    r.getGeneric().addAll(gTypes);
                                }
                            });
                    return realType;
                }).orElse(null);
    }

    public static void main(String[] args) {
        Matcher matcher = TYPE_PATTERN.matcher("AbstractGenericClass<AbstractGenericClass<Object>>");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        }
        matcher = TYPE_PATTERN.matcher("AbstractGenericClass");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        }
    }

}
