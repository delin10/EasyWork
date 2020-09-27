package nil.ed.easywork.generator.context;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easy.script.groovy.support.GroovyUtils;
import nil.ed.easywork.generator.config.AbstractTemplateConfig;
import nil.ed.easywork.generator.config.DefaultTemplateConfig;
import nil.ed.easywork.util.ClasspathFileUtils;
import nil.ed.easywork.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidelin.
 */
@Slf4j
public class PowerTemplateContext {

    private static final String CONFIG_PATH_START = "==>>";
    private static final String TPL_SUFFIX = "ftl";

    private static final Map<String, AbstractTemplateConfig> TEMPLATE_CONFIG_CACHE = new HashMap<>();

    private final String baseDir;

    public PowerTemplateContext(String baseDir) {
        this.baseDir = baseDir;
        load();
    }

    public AbstractTemplateConfig getTemplateConfig(String name) {
        return TEMPLATE_CONFIG_CACHE.get(name);
    }

    public Map<String, AbstractTemplateConfig> getTemplateConfigCache() {
        return TEMPLATE_CONFIG_CACHE;
    }

    private void load() {
        try {
            Utils.listFiles(baseDir, File::isFile, f -> {
                try {
                    if (!f.getName().endsWith(TPL_SUFFIX)) {
                        return;
                    }
                    StringBuilder configPathBuilder = new StringBuilder();
                    String text = Utils.readText(f.getAbsolutePath(), lines -> {
                        if (lines.size() > 0) {
                            String firstLine = lines.get(0);
                            if (firstLine.startsWith(CONFIG_PATH_START)) {
                                String name = firstLine.substring(CONFIG_PATH_START.length());
                                configPathBuilder.append(name);
                            } else {
                               log.warn("Template: {} User Default Template Config!", f.getName());
                            }
                        }
                        lines.remove(0);
                        return String.join(System.lineSeparator(), lines);
                    }, StandardCharsets.UTF_8);
                    AbstractTemplateConfig config;
                    if (configPathBuilder.length() == 0) {
                        config = new DefaultTemplateConfig();
                    } else {
                        String path = configPathBuilder.toString().trim();
                        String file = ClasspathFileUtils.getClassPath(path);
                        if (file  == null) {
                            throw new FileNotFoundException(path + " not found!");
                        }
                        String script = Utils.readText(file, StandardCharsets.UTF_8);
                        config = GroovyUtils.newInstance(script);
                        config.setTemplateText(text);
                    }
                    TEMPLATE_CONFIG_CACHE.put(config.getTemplateName(), config);

                } catch (IOException e) {
                    log.error("Failed to read template: {}!", f.getAbsolutePath(), e);
                }
            });
            if (log.isDebugEnabled()) {
                log.debug("Load complete! templates = {}", TEMPLATE_CONFIG_CACHE);
            }
        }catch (FileNotFoundException e) {
            log.error("Failed to load templates!", e);
            System.exit(1);
        }
    }


}
