package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Data
public class ControllerBean implements ResolveResult {

    private String id;

    private String path;

    private String basePackage;

    private List<ApiBean> apis = new LinkedList<>();

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        List<ControllerBean> ls = (List<ControllerBean>) ResolveContext.CXT.computeIfAbsent(getKey(), k -> new LinkedList<ControllerBean>());
        ls.add(this);
    }

    @Override
    public Object getResult() {
        return this;
    }

    @Override
    public String getKey() {
        return ResolveContext.CONTROLLERS;
    }
}
