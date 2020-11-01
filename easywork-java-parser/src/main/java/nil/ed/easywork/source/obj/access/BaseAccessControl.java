package nil.ed.easywork.source.obj.access;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Prefix;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.util.FlowUtils;

import java.util.Set;
import javax.lang.model.element.Modifier;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Setter
@Getter
public class BaseAccessControl implements Prefix {

    protected AccessEnum access = AccessEnum.PACKAGE;

    private boolean isFinal;

    private boolean isStatic;

    private boolean isAbstract;

    private boolean isDefault;

    private boolean isTransient;

    private boolean isVolatile;

    private boolean isSynchronized;

    private boolean isNative;

    private boolean isStrictfp;

    public void setFinal() {
        setFinal(true);
    }

    public void setStatic() {
        setStatic(true);
    }

    public void setAbstract() {
        setAbstract(true);
    }

    public void setDefault() {
        setDefault(true);
    }

    public void setTransient() {
        setTransient(true);
    }

    public void setVolatile() {
        setVolatile(true);
    }

    public void setSynchronized() {
        setSynchronized(true);
    }

    public void setNative() {
        setNative(true);
    }

    public void setStrictfp() {
        setStrictfp(true);
    }

    @Override
    public String getPrefix() {
        return AccessControlSupport.toString(this);
    }

    public static BaseAccessControl from(Set<Modifier> modifiers) {
        return FlowUtils.continueIfNotNull(modifiers)
                .map(AccessControlSupport::transToAccessControl)
                .orElse(AccessControlSupport.EMPTY);
    }

}
