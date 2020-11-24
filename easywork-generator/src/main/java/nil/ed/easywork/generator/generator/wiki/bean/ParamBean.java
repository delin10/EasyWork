package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author lidelin.
 */
@Data
@ToString(exclude = "parent")
public class ParamBean implements ResolveResult {

    private ResolveResult parent;

    private String name;

    private String type;

    private boolean required;

    private String defaultValue;

    private String comment;

    @Override
    public Object getResult() {
        return this;
    }

    @Override
    public String getKey() {
        return "params";
    }
}
