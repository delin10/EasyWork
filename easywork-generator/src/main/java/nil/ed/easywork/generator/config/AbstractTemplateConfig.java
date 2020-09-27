package nil.ed.easywork.generator.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin.
 */
public abstract class AbstractTemplateConfig implements TemplateAction {

    @Getter
    @Setter
    private String templateText;

    /**
     * 模版名称.
     *
     * @return 结果.
     */
    public String getTemplateName() {
        return this.getClass().getSimpleName();
    }

}
