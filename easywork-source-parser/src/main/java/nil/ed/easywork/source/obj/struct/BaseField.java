package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.FieldAccessControl;
import nil.ed.easywork.source.obj.render.Render;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class BaseField implements Statement {

    private String  name;

    private JavaType type;

    private FieldAccessControl access;

    private Render<BaseField> render;

    public BaseField(String name, JavaType type, FieldAccessControl access) {
        this.name = name;
        this.type = type;
        this.access = access;
    }

    public BaseField(String name, JavaType type) {
        this(name, type, FieldAccessControl.buildDefault());
    }

    @Override
    public List<Line> getStatement(int level) {
        return render.render(this, level);
    }

    @Override
    public String toString() {
        return new LineOutput().output(getStatement(0));
    }
}
