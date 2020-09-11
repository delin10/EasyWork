package nil.ed.easywork.generator.context;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.Utils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Slf4j
public class TemplateContext {

    public static final String ENTITY = "ENTITY";
    public static final String MODEL = "MODEL";
    public static final String VO = "VO";
    public static final String CONTROLLER = "CONTROLLER";
    public static final String MAPPER = "MAPPER";
    public static final String SERVICE = "SERVICE";
    public static final String SERVICE_IMPL = "SERVICE_IMPL";
    public static final String REPO_INTERFACE = "REPO";
    public static final String REPO_IMPL = "REPO_IMPL";
    public static final String MAPPER_XML = "MAPPER_XML";
    public static final String COND = "COND";
    public static final String BIZ_ERROR_TYPE = "BIZ_ERROR_TYPE";

    private static final Map<String, MutableTriple<String, String, String>> TEMPLATE_CACHE = new HashMap<>();

    static {
        TEMPLATE_CACHE.put(ENTITY, MutableTriple.of("entity", "%sEntity.java",""));
        TEMPLATE_CACHE.put(MODEL, MutableTriple.of("model", "%s.java", ""));
        TEMPLATE_CACHE.put(VO, MutableTriple.of("vo", "%sVO.java", ""));
        TEMPLATE_CACHE.put(CONTROLLER, MutableTriple.of("controller", "%sController.java", ""));
        TEMPLATE_CACHE.put(MAPPER, MutableTriple.of("dao", "%sMapper.java", ""));
        TEMPLATE_CACHE.put(SERVICE, MutableTriple.of("service", "%sService.java", ""));
        TEMPLATE_CACHE.put(SERVICE_IMPL, MutableTriple.of("service/impl", "%sServiceImpl.java", ""));
        TEMPLATE_CACHE.put(REPO_INTERFACE, MutableTriple.of("repository", "%sRepo.java", ""));
        TEMPLATE_CACHE.put(REPO_IMPL, MutableTriple.of("repository/impl", "%sRepositoryImpl.java", ""));
        TEMPLATE_CACHE.put(MAPPER_XML, MutableTriple.of("mapper_xml", "%sMapper.xml", ""));
        TEMPLATE_CACHE.put(COND, MutableTriple.of("condition", "%sQueryCondition.java", ""));
        TEMPLATE_CACHE.put(BIZ_ERROR_TYPE, MutableTriple.of("exception", "%sBizErrorType.java", ""));
    }

    private final String baseDir;

    public TemplateContext(String baseDir) {
        this.baseDir = baseDir;
        load();
    }

    public MutableTriple<String, String, String> getTemplate(String name) {
        return TEMPLATE_CACHE.get(name);
    }

    public Map<String, MutableTriple<String, String, String>> getTemplateCache() {

        return TEMPLATE_CACHE;
    }

    private void load() {
        try {
            Utils.listFiles(baseDir, File::isFile, f -> {
                try {
                    StringBuilder nameBuilder = new StringBuilder();
                    String text = Utils.readText(f.getAbsolutePath(), lines -> {
                        if (lines.size() > 0) {
                            String firstLine = lines.get(0);
                            if (firstLine.startsWith("!=")) {
                                String name = firstLine.substring("!=".length());
                                nameBuilder.append(name);
                            }
                        }
                        lines.remove(0);
                        return String.join(System.lineSeparator(), lines);
                    }, StandardCharsets.UTF_8);
                    if (nameBuilder.length() == 0) {
                        nameBuilder.append(FilenameUtils.getBaseName(f.getName()));
                    }
                    TEMPLATE_CACHE.putIfAbsent(nameBuilder.toString(), 
                            MutableTriple.of("", "%s" + nameBuilder.toString(), text));
                    TEMPLATE_CACHE.get(nameBuilder.toString()).right = text;
                } catch (IOException e) {
                    log.error("Failed to read template: {}!", f.getAbsolutePath(), e);
                }
            });
            TEMPLATE_CACHE.entrySet().removeIf(entry -> StringUtils.isBlank(entry.getValue().right));
            if (log.isDebugEnabled()) {
                log.debug("Load complete! templates = {}", TEMPLATE_CACHE);
            }
        }catch (FileNotFoundException e) {
            log.error("Failed to load templates!", e);
        }
    }

}
