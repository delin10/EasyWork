package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;
import lombok.ToString;
import nil.ed.easywork.generator.generator.wiki.enums.ParamType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Data
@ToString(exclude = "parent")
public class ParamsContainer implements ResolveResult{

    private ResolveResult parent;

    private String id;

    private List<ParamBean> params = new LinkedList<>();

    private ParamType type;

    @Override
    public Object getResult() {
        return this;
    }

    public ParamBean initAndAddParam() {
        ParamBean paramBean = new ParamBean();
        paramBean.setParent(this);
        params.add(paramBean);
        return paramBean;
    }

    @Override
    public String getKey() {
        return null;
    }
}
