package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.JavaAnnotation;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.source.obj.render.MethodRender;
import nil.ed.easywork.source.obj.render.Render;
import nil.ed.easywork.source.obj.type.JavaType;

import java.util.*;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class BaseMethod implements Statement, Notable {

    private String name;

    private BaseAccessControl access;

    private JavaType returnType;

    private List<BaseField> params = new LinkedList<>();

    private Render<BaseMethod> render = MethodRender.NO_BODY_RENDER;

    private List<JavaAnnotation> annotations;

    public BaseMethod(String name, JavaType returnType) {
        this(name, AccessControlSupport.EMPTY, returnType);
    }

    public BaseMethod(String name, BaseAccessControl access, JavaType returnType) {
        this(name, access, returnType, Collections.emptySet());
    }

    public BaseMethod(String name, BaseAccessControl access, JavaType returnType, Collection<BaseField> params) {
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

    @Override
    public List<JavaAnnotation> annotations() {
        if (annotations == null) {
            annotations = new LinkedList<>();
        }
        return annotations;
    }
}
