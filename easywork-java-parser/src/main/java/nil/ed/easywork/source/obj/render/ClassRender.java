package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.type.BaseClass;
import nil.ed.easywork.util.FlowUtils;
import nil.ed.easywork.util.StringBuilderUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class ClassRender implements Render<BaseClass> {

    @Override
    public List<Line> render(BaseClass baseClass, int level) {
        List<Line> lines = new LinkedList<>();
        StringBuilder header = new StringBuilder();
        StringBuilderUtils.appendSeparateBySpace(header,
                baseClass.getAccess().getPrefix(),
                "class",
                baseClass.getName(),
                "{");
        lines.add(new Line(header, level));
        FlowUtils.iterateIfNotNull(baseClass.getFields()).forEach(f -> {
            lines.addAll(f.getStatement(level + 1));
        });
        FlowUtils.iterateIfNotNull(baseClass.getMethods()).forEach(method -> {
            lines.addAll(method.getStatement(level + 1));
        });
        lines.add(new Line("}", level));
        return lines;
    }

}
