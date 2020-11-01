package nil.ed.easywork.source.parser.processor;

import com.sun.tools.javac.tree.JCTree;
import lombok.Getter;
import nil.ed.easywork.source.obj.JavaAnnotation;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.source.obj.type.support.TypeSupport;
import nil.ed.easywork.source.parser.support.ParserSupport;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/5/28
 **/
@Getter
class FiledInfoParseConsumer implements Function<JCTree.JCVariableDecl, BaseField> {

    @Override
    public BaseField apply(JCTree.JCVariableDecl decl) {
        JCTree.JCExpression type = decl.vartype;
        List<JavaAnnotation> annotationList = decl.getModifiers().getAnnotations()
                .stream()
                .map(ParserSupport::getJavaAnnotation)
                .collect(Collectors.toList());

        BaseField field = new BaseField(decl.name.toString(),
                ParserSupport.getJavaType(type),
                AccessControlSupport.getBaseAccessControl(decl));
        field.annotations().addAll(annotationList);
        return field;
    }

}
