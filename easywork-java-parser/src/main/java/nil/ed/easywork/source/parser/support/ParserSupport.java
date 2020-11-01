package nil.ed.easywork.source.parser.support;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Name;
import nil.ed.easywork.source.obj.JavaAnnotation;
import nil.ed.easywork.source.obj.struct.anno.AbstractAnnotationValue;
import nil.ed.easywork.source.obj.struct.anno.ArrayAnnotationValue;
import nil.ed.easywork.source.obj.struct.anno.NestedAnnotationValue;
import nil.ed.easywork.source.obj.struct.anno.StringAnnotationValue;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.util.FlowUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class ParserSupport {

    public static final ThreadLocal<List<ImportItem>> IMPORT_CTX = new ThreadLocal<>();

    private static final Map<String, JavaType> TYPE_SET;
    static {
        TYPE_SET = Stream.of( byte.class,
                short.class,
                boolean.class,
                char.class,
                int.class,
                float.class,
                double.class,
                Byte.class,
                Short.class,
                Boolean.class,
                Character.class,
                Integer.class,
                Float.class,
                Double.class,
                String.class)
                .map(Class::getName)
                .collect(Collectors.toMap(Function.identity(), JavaType::create));
        TYPE_SET.put("void", JavaType.create("void"));
    }

    public static <T extends Tree> String getIdentifier(Tree tree) {
        return FlowUtils.continueIfNotNull(tree)
                .flatMap(x -> FlowUtils.continueIfValidCast(tree, JCTree.JCIdent.class))
                .map(JCTree.JCIdent::getName)
                .map(Name::toString)
                .orElse(null);
    }

    public static <T extends AnnotationTree> JavaAnnotation getJavaAnnotation(T anno) {
        String type = getIdentifier(anno.getAnnotationType());

        JavaAnnotation annotation = new JavaAnnotation(getJavaType(type));
        List<? extends ExpressionTree> args = anno.getArguments();
        List<AbstractAnnotationValue> values = getAnnotationValueList(args);
        annotation.attributes().addAll(values);
        return annotation;
    }

    public static List<AbstractAnnotationValue> getAnnotationValueList(List<? extends ExpressionTree> args) {
        return FlowUtils.continueStreamIfNotNull(args)
                .map(ParserSupport::parseAnnotationValue)
                .collect(Collectors.toList());
    }

    public static Optional<AbstractAnnotationValue> getAnnotationValue(ExpressionTree exp, BiFunction<ExpressionTree, String, Optional<AbstractAnnotationValue>> function) {
        String name = FlowUtils.continueIfValidCast(exp, JCTree.JCAssign.class)
                .map(JCTree.JCAssign::getVariable)
                .map(ParserSupport::getIdentifier)
                .orElse("value");
        Optional<ExpressionTree> value = FlowUtils.continueIfValidCast(exp, JCTree.JCAssign.class).map(agn -> agn.rhs);
        if (!value.isPresent()) {
            value = FlowUtils.continueIfValidCast(exp, JCTree.JCAnnotation.class).map(Function.identity());
        }
        return function.apply(value.orElse(null), name);
    }


    public static Optional<AbstractAnnotationValue> getLiteralAnnotationValue(ExpressionTree exp) {
        return getAnnotationValue(exp, ParserSupport::getLiteralAnnotationValueInternal);
    }

    public static Optional<AbstractAnnotationValue> getNestedAnnotationValue(ExpressionTree exp) {
        return getAnnotationValue(exp, ParserSupport::getNestedAnnotationValueInternal);
    }

    public static Optional<AbstractAnnotationValue> getArrayAnnotationValue(ExpressionTree exp) {
        return getAnnotationValue(exp, ParserSupport::getArrayAnnotationValueInternal);
    }

    private static Optional<AbstractAnnotationValue> getLiteralAnnotationValueInternal(ExpressionTree tree, String name) {
        return FlowUtils.continueIfValidCast(tree, JCTree.JCLiteral.class)
                .map(x -> {
                    StringAnnotationValue value = new StringAnnotationValue(name);
                    value.setTag(x.typetag);
                    value.setValue(String.valueOf(x.getValue()));
                    return value;
                });
    }

    private static Optional<AbstractAnnotationValue> getNestedAnnotationValueInternal(ExpressionTree tree, String name) {
        return FlowUtils.continueIfValidCast(tree, JCTree.JCAnnotation.class)
                .map(x -> {
                    NestedAnnotationValue value = new NestedAnnotationValue(name);
                    JavaAnnotation annotation = getJavaAnnotation(x);
                    value.setValue(annotation);
                    return value;
                });
    }

    public static Optional<AbstractAnnotationValue> getArrayAnnotationValueInternal(ExpressionTree exp, String name) {
        return FlowUtils.continueIfValidCast(exp, JCTree.JCNewArray.class)
                    .map(x -> {
                        ArrayAnnotationValue anv = new ArrayAnnotationValue(name);
                        List<AbstractAnnotationValue> values = FlowUtils.continueStreamIfNotNull(x.elems)
                                .map(ParserSupport::parseAnnotationValue)
                                .collect(Collectors.toList());
                        anv.getValueList().addAll(values);
                        return anv;
                    });
    }

    public static AbstractAnnotationValue parseAnnotationValue(ExpressionTree tree) {
        Optional<AbstractAnnotationValue> valueOptional = getLiteralAnnotationValue(tree);
        if (!valueOptional.isPresent()) {
            valueOptional = getNestedAnnotationValue(tree);
        }

        if (!valueOptional.isPresent()) {
            valueOptional = getArrayAnnotationValue(tree);
        }

        if (!valueOptional.isPresent()) {
            throw new IllegalArgumentException("" + tree.getClass() + "=" + tree);
        }
        return valueOptional.get();
    }

    public static JavaType getJavaTypeInternal(List<ImportItem> importCtx, String type) {
        return importCtx
                .stream()
                .filter(i -> !i.isWildcard() && i.getContent().endsWith(type))
                .findFirst()
                .map(ImportItem::getContent)
                .map(JavaType::create)
                .orElse(new JavaType(type));
    }

    public static JavaType getJavaType(String type) {
        if (TYPE_SET.containsKey(type)) {
            return TYPE_SET.get(type);
        }
        return getJavaTypeInternal(IMPORT_CTX.get(), type);
    }

    public static boolean isVoidReturnType(JavaType type) {
        return getJavaType("void").getName().equals(type.getName());
    }

    public static JavaType getJavaType(JCTree exp) {
        if (exp instanceof JCTree.JCPrimitiveTypeTree) {
            JCTree.JCPrimitiveTypeTree pt = (JCTree.JCPrimitiveTypeTree) exp;
            return TYPE_SET.get(pt.getPrimitiveTypeKind().name().toLowerCase());
        } else if (exp instanceof JCTree.JCArrayTypeTree) {
            JCTree.JCArrayTypeTree at = (JCTree.JCArrayTypeTree) exp;
            JavaType compType = getJavaType(at.elemtype);
//            JavaType type = getJavaType(at.getType());
        } else if (exp instanceof JCTree.JCIdent) {
            return getJavaType(getIdentifier(exp));
        }
        return null;
    }

}
