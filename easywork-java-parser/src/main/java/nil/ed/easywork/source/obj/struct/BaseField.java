package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.JavaAnnotation;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.source.obj.render.FieldRender;
import nil.ed.easywork.source.obj.render.Render;
import nil.ed.easywork.source.obj.type.JavaType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class BaseField implements Statement, Notable {

    private String  name;

    private JavaType type;

    private BaseAccessControl access;

    private Render<BaseField> render = new FieldRender();

    private List<JavaAnnotation> annotations;

    public BaseField(String name, JavaType type) {
        this(name, type, AccessControlSupport.EMPTY);
    }

    public BaseField(String name, JavaType type, BaseAccessControl access) {
        this.name = name;
        this.type = type;
        this.access = access;
    }

    @Override
    public List<JavaAnnotation> annotations() {
        if (annotations == null) {
            annotations = new LinkedList<>();
        }
        return annotations;
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
