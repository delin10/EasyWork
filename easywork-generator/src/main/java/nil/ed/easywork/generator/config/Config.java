package nil.ed.easywork.generator.config;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.util.FlowUtils;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/4
 **/
@Setter
@Getter
public class Config {

    private String basePkg = "";

    private String basePath = "/";

    private String prefix = "";

    private String profile = "";

    public final Map<String, ClassIndexConfig> typeCache;

    public final Map<Pattern, ClassIndexConfig> PATTERN_MAP;

    {
        ClassIndexConfigLoader loader = new ClassIndexConfigLoader();
        typeCache = loader.load();
        PATTERN_MAP = typeCache.entrySet().stream()
                .collect(Collectors.toMap(e -> Pattern.compile("\\b"+ e.getKey() +"\\b"), Map.Entry::getValue));
    }

    public boolean needImport(String clazz) {
        return Optional.ofNullable(typeCache.get(clazz.substring(clazz.lastIndexOf('.') + 1)))
                .map(ClassIndexConfig::isNeedImport)
                .orElse(true);
    }

    public String getType(String type) {
        return FlowUtils.notNullOrElse(typeCache.get(type.substring(type.lastIndexOf('.') + 1)), ClassIndexConfig::getClazz, type);
    }

}
