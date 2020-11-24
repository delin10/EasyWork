package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Data
public class CompositeApiBean implements ResolveResult {

    private List<ApiBean> apiBeanList = new LinkedList<>();

    @Override
    public Object getResult() {
        return apiBeanList;
    }

    @Override
    public String getKey() {
        return null;
    }
}
