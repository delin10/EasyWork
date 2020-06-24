package nil.ed.easywork.source.obj.access;

import java.util.ArrayList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class ClassAccessControl extends BaseAccessControl {

    private boolean isAbstract;


    public ClassAccessControl(AccessEnum access, boolean isFinal, boolean isStatic, boolean isAbstract, boolean isDefault) {
        super(access, isFinal, isStatic);
        this.isAbstract = isAbstract;
    }

    public ClassAccessControl() {
        this(AccessEnum.PUBLIC, false, false, false, false);
    }

    @Override
    public String getPrefix() {
        List<String> list = new ArrayList<>();
        list.add(access.getName());

        /*
         * 说明是接口默认方法
         */
        if (isStatic) {
            list.add("static");
        }

        if (isAbstract) {
            list.add("abstract");
        } else if (isFinal) {
            list.add("final");
        }

        return String.join(" ", list);
    }
}
