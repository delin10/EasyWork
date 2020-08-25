package nil.ed.easywork.generator.context;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.Utils;
import org.apache.commons.io.FilenameUtils;

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
    public static final String MAPPER_XML = "MAPPER_XML";

    private Map<String, String> template = new HashMap<>();

    private String baseDir;

    public TemplateContext(String baseDir) {
        this.baseDir = baseDir;
        load();
    }

    public String getTemplate(String name) {
        return template.get(name);
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
                    template.put(nameBuilder.toString(), text);
                } catch (IOException e) {
                    log.error("Failed to read template: {}!", f.getAbsolutePath(), e);
                }
            });
            if (log.isDebugEnabled()) {
                log.debug("Load complete! templates = {}", template);
            }
        }catch (FileNotFoundException e) {
            log.error("Failed to load templates!", e);
        }
    }

}
