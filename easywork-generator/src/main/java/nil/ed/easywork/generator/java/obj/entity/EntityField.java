package nil.ed.easywork.generator.java.obj.entity;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.render.FieldRender;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.JavaType;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Setter
@Getter
public class EntityField extends BaseField {

    private boolean primary;

    public EntityField(String name, JavaType type) {
        super(name, type);
        setRender(new EntityFieldRender());
    }

    private class EntityFieldRender extends FieldRender {
        @Override
        public List<Line> render(BaseField baseField, int level) {
            List<Line> lines = new LinkedList<>();
            StringBuilder builder = new StringBuilder();
            if (primary) {
                Collections.addAll(lines, new Line( "@Id", level),
                        new Line( "@GeneratedValue(strategy = GenerationType.IDENTITY)", level));
            }
            lines.addAll(super.render(baseField, level));
            return lines;
        }
    }

}
