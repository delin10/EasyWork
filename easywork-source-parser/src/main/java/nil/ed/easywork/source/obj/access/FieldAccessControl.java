package nil.ed.easywork.source.obj.access;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
public class FieldAccessControl extends BaseAccessControl {



    private boolean isVolatile;

    private boolean isTransient;



    public FieldAccessControl() {
        this(false, false, AccessEnum.PRIVATE);
    }

    public FieldAccessControl(AccessEnum access) {
        this(false, false, access);
    }

    public FieldAccessControl(boolean isFinal, boolean isStatic, AccessEnum access) {
        this(isFinal, isStatic, false, false, access);
    }

    public FieldAccessControl(boolean isFinal, boolean isStatic, boolean isVolatile, boolean isTransient, AccessEnum access) {
       super(access, isFinal, isStatic);
        if (isVolatile && isFinal) {
            throw new IllegalArgumentException("A field cannot both be volatile and final!");
        }
        this.isVolatile = isVolatile;
        this.isTransient = isTransient;
    }

    public static FieldAccessControl buildSimple(boolean isFinal, boolean isStatic, AccessEnum access) {
        return new FieldAccessControl(isFinal, isStatic, access);
    }

    public static FieldAccessControl buildDefault() {
        return new FieldAccessControl();
    }

    @Override
    public String getPrefix() {
        return toString();
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        list.add(access.getName());
        if (isStatic) {
            list.add("static");
        }

        if (isTransient) {
            list.add("transient");
        }

        if (isVolatile) {
            list.add("volatile");
        } else if (isFinal) {
            list.add("final");
        }

        return String.join(" ", list);

    }
}
