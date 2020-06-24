package nil.ed.easywork.source.obj.access;

import java.util.ArrayList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class MethodAccessControl extends BaseAccessControl {

    private boolean isAbstract;

    private boolean isDefault;

    public MethodAccessControl(AccessEnum access, boolean isFinal, boolean isStatic, boolean isAbstract, boolean isDefault) {
        super(access, isFinal, isStatic);
        this.isAbstract = isAbstract;
        this.isDefault = isDefault;
    }

    public MethodAccessControl() {
        this(AccessEnum.PUBLIC, false, false, false, false);
    }

    @Override
    public String getPrefix() {
        List<String> list = new ArrayList<>();
        list.add(access.getName());

        /*
         * 说明是接口默认方法
         */
        if (isDefault) {
            list.add("default");
        } else {
            if (isStatic) {
                list.add("static");
            }

            if (isAbstract) {
                list.add("abstract");
            }

            if (isFinal) {
                list.add("final");
            }

        }
        return String.join(" ", list);
    }
}
