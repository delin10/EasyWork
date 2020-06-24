package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.MethodAccessControl;
import nil.ed.easywork.source.obj.render.MethodRender;
import nil.ed.easywork.source.obj.render.Render;

import java.util.*;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class BaseMethod implements Statement {

    private String name;

    private MethodAccessControl access;

    private JavaType returnType;

    private Set<Param> params = new LinkedHashSet<>();

    private Render<BaseMethod> render = MethodRender.NO_BODY_RENDER;

    public BaseMethod(String name, JavaType returnType) {
        this(name, new MethodAccessControl(), returnType);
    }

    public BaseMethod(String name, MethodAccessControl access, JavaType returnType) {
        this(name, access, returnType, Collections.emptySet());
    }

    public BaseMethod(String name, MethodAccessControl access, JavaType returnType, Collection<Param> params) {
        this.name = name;
        this.access = access;
        this.returnType = returnType;
        if (params != null) {
            this.params.addAll(params);
        }
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
