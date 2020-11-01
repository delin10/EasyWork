package nil.ed.easywork.source.obj.struct.anno;

import com.sun.tools.javac.code.TypeTag;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lidelin.
 */
@Getter
@Setter
public class StringAnnotationValue extends AbstractAnnotationValue {

    private String value;

    private TypeTag tag;

    public StringAnnotationValue(String name) {
        super(name);
    }

    public String value() {
        return value;
    }

}
