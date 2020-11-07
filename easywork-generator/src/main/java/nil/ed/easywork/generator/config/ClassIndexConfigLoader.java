package nil.ed.easywork.generator.config;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.ClasspathFileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
@Slf4j
public class ClassIndexConfigLoader {

    private final Yaml yaml = new Yaml();

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, ClassIndexConfig> load() {
        try (InputStream in
                     = Files.newInputStream(Paths.get(ClasspathFileUtils.getClassPath("/"), "import.classes.yml"))){
            Map<Object, Object> map = yaml.load(in);
            return map.entrySet().stream().map(e -> {
                ClassIndexConfig config = new ClassIndexConfig();
                config.setClazz(e.getKey().toString());
                if (e.getValue() instanceof Map) {
                    config.setNeedImport((Boolean) (((Map) e.getValue()).getOrDefault("needImport", true)));
                }
                return config;
            }).collect(Collectors.toMap(c -> c.getClazz().substring(c.getClazz().lastIndexOf('.') + 1), Function.identity(), (a, b) -> a));
        } catch (IOException e) {
            log.error("Failed to load class index.", e);
        }
        return Collections.emptyMap();
    }

}
