package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.BaseMethod;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class MethodRender implements Render<BaseMethod> {

    public static final MethodRender NO_BODY_RENDER = new MethodRender(new NoBodySupplier());
    public static final MethodRender EMPTY_BODY_RENDER = new MethodRender(new EmptyBodySupplier());

    private final StatementSupplier statementSupplier;

    public MethodRender(StatementSupplier statementSupplier) {
        this.statementSupplier = statementSupplier;
    }

    @Override
    public List<Line> render(BaseMethod baseMethod, int level) {
        LinkedList<Line> ls = new LinkedList<>();
        ls.add(new Line(getHeader(baseMethod), level));

        if (statementSupplier != null) {
            List<Line> statements = statementSupplier.apply(baseMethod, level + 1);
            if (statements != null) {
                ls.getLast().tailAppend(" {");
                ls.addAll(statements);
                ls.add(new Line("}", level));
            } else {
                ls.getLast().tailAppend(";");
            }
        } else {
            ls.getLast().tailAppend(";");
        }
        return ls;
    }

    private String getHeader(BaseMethod method) {
        String paramsStr = method.getParams().stream()
                .map(f -> f.getAccess().getPrefix() + " " + f.getType().getName() + " " + f.getName())
                .collect(Collectors.joining(", "));
        return method.getAccess().getPrefix() +
                " " +
                method.getReturnType().getName() +
                " " +
                method.getName() +
                "(" +
                paramsStr.trim() +
                ")";
    }

    public static Line getNewStatement(String typeSimpleName, String varName, String...params) {
        StringBuilder builder = new StringBuilder();
        builder.append(typeSimpleName);
        builder.append(" ");
        builder.append(varName == null ? NamingTranslatorSingleton.PASCAL_TO_CAMEL.trans(typeSimpleName) : varName);
        builder.append(" = new ");
        builder.append(typeSimpleName);
        builder.append("(");
        if (params != null && params.length != 0) {
            builder.append(String.join(", ", params));
        }
        builder.append(")");
        return new Line(builder.toString(), 0);
    }

}
