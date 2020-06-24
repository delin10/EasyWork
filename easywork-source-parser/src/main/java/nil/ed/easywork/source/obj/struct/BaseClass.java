package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.ClassAccessControl;
import nil.ed.easywork.source.obj.render.ClassRender;
import nil.ed.easywork.source.obj.render.Render;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Setter
@Getter
public class BaseClass implements Statement{

    private String pkg;

    private String name;

    private ClassAccessControl access;

    private List<String> imports = new LinkedList<>();

    private List<BaseField> fields = new LinkedList<>();

    private List<BaseMethod> methods = new LinkedList<>();

    private Render<BaseClass> render = new ClassRender();

    public BaseClass(String pkg, String name, ClassAccessControl access) {
        this.pkg = pkg;
        this.name = name;
        this.access = access;
    }

    public BaseClass(String pkg, String name) {
        this(pkg, name, new ClassAccessControl());
    }

    public BaseClass() {
        this(null, null);
    }

    public String getFullyName() {
        return (StringUtils.isBlank(pkg) ? "" : pkg + ".") + name;
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
