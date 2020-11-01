package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.struct.BaseField;

import java.util.Collections;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class FieldRender implements Render<BaseField> {

    @Override
    public List<Line> render(BaseField baseField, int level) {
        Line line = new Line(baseField.getAccess().getPrefix() + " " + baseField.getType().getName() + " " + baseField.getName() + ";", level);
        line.setIndent(level);
        return Collections.singletonList(line);
    }

}
