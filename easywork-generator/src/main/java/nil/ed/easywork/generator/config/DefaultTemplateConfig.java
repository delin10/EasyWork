package nil.ed.easywork.generator.config;

/**
 * @author lidelin.
 */
public class DefaultTemplateConfig extends AbstractTemplateConfig {
    @Override
    public String getTemplateName() {
        return "DEFAULT-" + System.nanoTime();
    }
}
