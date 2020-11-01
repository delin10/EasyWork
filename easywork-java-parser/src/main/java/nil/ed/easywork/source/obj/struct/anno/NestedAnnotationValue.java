package nil.ed.easywork.source.obj.struct.anno;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.JavaAnnotation;

/**
 * @author lidelin.
 */
@Setter
@Getter
public class NestedAnnotationValue extends AbstractAnnotationValue {

    private JavaAnnotation value;

    public NestedAnnotationValue(String name) {
        super(name);
    }

    public JavaAnnotation value() {
        return value;
    }

}
