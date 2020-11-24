package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Data
@ToString(exclude = "parent")
public class ApiBean implements ResolveResult {

    private ResolveResult parent;

    private String id;

    private String path;

    private String method;

    private List<ParamsContainer> containers = new LinkedList<>();

    @Override
    public Object getResult() {
        return this;
    }

    @Override
    public String getKey() {
        return null;
    }

}
