package nil.ed.easywork.generator.context;

import lombok.ToString;
import nil.ed.easywork.source.obj.struct.BaseClass;

/**
 * @author delin10
 * @since 2020/6/2
 **/
@ToString(callSuper = true)
public class JavaContext extends AbstractContext<BaseClass> {

    private String pkgName;

    public String getPkgName() {
        return pkgName;
    }
}
