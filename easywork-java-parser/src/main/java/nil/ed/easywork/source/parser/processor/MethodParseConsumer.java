package nil.ed.easywork.source.parser.processor;

import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.BaseMethod;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.source.parser.support.ParserSupport;
import nil.ed.easywork.util.FlowUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class MethodParseConsumer implements Function<JCTree.JCMethodDecl, BaseMethod> {

    @Override
    public BaseMethod apply(JCTree.JCMethodDecl decl) {
        String name = decl.getName().toString();
        JavaType returnType = ParserSupport.getJavaType(decl.getReturnType());
        BaseAccessControl control = BaseAccessControl.from(decl.getModifiers().getFlags());
        List<BaseField> fieldList = FlowUtils.continueStreamIfNotNull(decl.getParameters())
                .map(ProcessorSingleton.FIELD_TREE_PROCESSOR::apply)
                .map(n -> FlowUtils.continueIfValidCast(n, BaseField.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return new BaseMethod(name, control, returnType, fieldList);
    }

}
