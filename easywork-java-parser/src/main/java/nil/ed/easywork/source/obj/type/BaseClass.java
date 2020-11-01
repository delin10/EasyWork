package nil.ed.easywork.source.obj.type;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import nil.ed.easywork.source.obj.JavaAnnotation;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.source.obj.render.ClassRender;
import nil.ed.easywork.source.obj.render.Render;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.BaseMethod;
import nil.ed.easywork.source.obj.struct.Statement;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Setter
@Getter
@Accessors(chain = true)
public class BaseClass implements Statement {

    private String pkg;

    private String name;

    private boolean isInterface;

    private BaseAccessControl access;

    private JavaType extendsClazz;

    private final List<JavaType> implInterfaces = new LinkedList<>();

    private final List<TypeParam> typeParams = new LinkedList<>();

    private final List<ImportItem> imports = new LinkedList<>();

    private final List<BaseField> fields = new LinkedList<>();

    private final List<BaseMethod> methods = new LinkedList<>();

    private final List<JavaAnnotation> annotations = new LinkedList<>();

    private Render<BaseClass> render = new ClassRender();

    public BaseClass(String pkg, String name, BaseAccessControl access) {
        this.pkg = pkg;
        this.name = name;
        this.access = access;
    }

    public BaseClass(String pkg, String name) {
        this(pkg, name, AccessControlSupport.EMPTY);
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
