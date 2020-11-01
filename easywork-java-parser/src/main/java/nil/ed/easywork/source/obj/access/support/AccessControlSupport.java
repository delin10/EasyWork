package nil.ed.easywork.source.obj.access.support;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.access.AccessEnum;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.util.FlowUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

/**
 * @author lidelin.
 */
public class AccessControlSupport {

    public static BaseAccessControl EMPTY = new BaseAccessControl(){
        @Override
        public void setAccess(AccessEnum access) {
        }

        @Override
        public void setFinal(boolean isFinal) {
        }

        @Override
        public void setStatic(boolean isStatic) {
        }

        @Override
        public void setAbstract(boolean isAbstract) {
        }

        @Override
        public void setDefault(boolean isDefault) {
        }

        @Override
        public void setTransient(boolean isTransient) {
        }

        @Override
        public void setVolatile(boolean isVolatile) {
        }

        @Override
        public void setSynchronized(boolean isSynchronized) {
        }

        @Override
        public void setNative(boolean isNative) {
        }

        @Override
        public void setStrictfp(boolean isStrictfp) {
        }
    };

    private static final Map<Modifier, Consumer<BaseAccessControl>> FLAG_SETTER_MAP = new HashMap<>(8, 1);
    private static final Map<Modifier, Consumer<BaseAccessControl>> ACCESS_SETTER_MAP = new HashMap<>(4, 1);

    static {
        FLAG_SETTER_MAP.put(Modifier.FINAL, BaseAccessControl::setFinal);
        FLAG_SETTER_MAP.put(Modifier.VOLATILE, BaseAccessControl::setVolatile);
        FLAG_SETTER_MAP.put(Modifier.TRANSIENT, BaseAccessControl::setTransient);
        FLAG_SETTER_MAP.put(Modifier.STATIC, BaseAccessControl::setStatic);
        FLAG_SETTER_MAP.put(Modifier.NATIVE, BaseAccessControl::setNative);
        FLAG_SETTER_MAP.put(Modifier.DEFAULT, BaseAccessControl::setDefault);
        FLAG_SETTER_MAP.put(Modifier.ABSTRACT, BaseAccessControl::setAbstract);
        FLAG_SETTER_MAP.put(Modifier.STRICTFP, BaseAccessControl::setStrictfp);
        FLAG_SETTER_MAP.put(Modifier.SYNCHRONIZED, BaseAccessControl::setSynchronized);

        ACCESS_SETTER_MAP.put(Modifier.PUBLIC, c -> c.setAccess(AccessEnum.PUBLIC));
        ACCESS_SETTER_MAP.put(Modifier.PRIVATE, c -> c.setAccess(AccessEnum.PRIVATE));
        ACCESS_SETTER_MAP.put(Modifier.PROTECTED, c -> c.setAccess(AccessEnum.PROTECTED));
    }

    public static BaseAccessControl transToAccessControl(Set<Modifier> modifiers) {
        BaseAccessControl control = new BaseAccessControl();
        FlowUtils.continueStreamIfNotNull(modifiers)
                .forEach(m -> {
                    switch (m) {
                        case PUBLIC:
                        case PRIVATE:
                        case PROTECTED:
                            FlowUtils.continueIfNotNull(ACCESS_SETTER_MAP.get(m))
                                    .ifPresent(setter -> setter.accept(control));
                            break;
                        default:
                            FlowUtils.continueIfNotNull(FLAG_SETTER_MAP.get(m))
                                    .ifPresent(setter -> setter.accept(control));
                            break;

                    }
                });
        return control;
    }

    public static BaseAccessControl getBaseAccessControl(JCTree.JCVariableDecl decl) {
        return getBaseAccessControl(decl::getModifiers);
    }

    public static BaseAccessControl getBaseAccessControl(ClassTree tree) {
        return getBaseAccessControl(tree::getModifiers);
    }

    public static BaseAccessControl getBaseAccessControl(Supplier<ModifiersTree> modifiersSupplier) {
        return FlowUtils.continueIfNotNull(modifiersSupplier.get())
                .map(mods -> FlowUtils.continueIfNotNull(FlowUtils.continueStreamIfNotNull(mods.getFlags())
                        .collect(Collectors.toSet()))
                        .map(AccessControlSupport::transToAccessControl)
                        .orElse(EMPTY))
                .orElse(EMPTY);
    }

    public static String toString(BaseAccessControl ac) {
        List<String> modifiers = new LinkedList<>();
        modifiers.add(ac.getAccess().getName());
        if (ac.isNative()) {
            modifiers.add("native");
        }
        if (ac.isSynchronized()) {
            modifiers.add("synchronized");
        }
        if (ac.isStatic()) {
            modifiers.add("static");
        }
        if (ac.isAbstract()) {
            modifiers.add("abstract");
        }
        if (ac.isDefault()) {
            modifiers.add("default");
        }
        if (ac.isFinal()) {
            modifiers.add("final");
        }
        if (ac.isStrictfp()) {
            modifiers.add("strictfp");
        }
        if (ac.isTransient()) {
            modifiers.add("transient");
        }
        if (ac.isVolatile()) {
            modifiers.add("volatile");
        }
        return String.join(" ", modifiers);
    }

}
