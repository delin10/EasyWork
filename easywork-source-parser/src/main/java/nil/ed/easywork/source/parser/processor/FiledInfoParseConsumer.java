package nil.ed.easywork.source.parser.processor;

import com.sun.tools.javac.tree.JCTree;
import lombok.Getter;
import nil.ed.easywork.source.obj.access.AccessEnum;
import nil.ed.easywork.source.obj.access.FieldAccessControl;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.JavaType;

import javax.lang.model.element.Modifier;
import java.util.Set;
import java.util.function.Function;

/**
 * @author delin10
 * @since 2020/5/28
 **/
@Getter
class FiledInfoParseConsumer implements Function<JCTree.JCVariableDecl, BaseField> {

    @Override
    public BaseField apply(JCTree.JCVariableDecl decl) {
        JCTree.JCExpression expression = decl.vartype;
        String type = "Unknown";
        if (expression instanceof JCTree.JCIdent) {
            type = ((JCTree.JCIdent)expression).name.toString();
        } else if (expression instanceof JCTree.JCTypeApply) {
            // 泛型的情况
            JCTree.JCTypeApply typeApply = (JCTree.JCTypeApply) expression;

        }
        return new BaseField(decl.name.toString(),
                new JavaType(type),
                toAccessControl(decl.getModifiers().getFlags()));
    }

    private FieldAccessControl toAccessControl(Set<Modifier> mods) {
        AccessEnum accessEnum = AccessEnum.PACKAGE;
        boolean isFinal = false;
        boolean isVolatile = false;
        boolean isStatic = false;
        boolean isTransient = false;
        for (Modifier modifier : mods) {
            if (modifier == Modifier.PRIVATE) {
                accessEnum = AccessEnum.PRIVATE;
            } else if (modifier == Modifier.PUBLIC) {
                accessEnum = AccessEnum.PUBLIC;
            } else if (modifier == Modifier.PROTECTED) {
                accessEnum = AccessEnum.PROTECTED;
            } else if (modifier == Modifier.TRANSIENT) {
                isTransient = true;
            } else if (modifier == Modifier.FINAL) {
                isFinal = true;
            } else if (modifier == Modifier.VOLATILE) {
                isVolatile = true;
            } else if (modifier == Modifier.STATIC) {
                isStatic = true;
            }
        }
        return new FieldAccessControl(isFinal, isStatic,
                isVolatile, isTransient, accessEnum);
    }

}
