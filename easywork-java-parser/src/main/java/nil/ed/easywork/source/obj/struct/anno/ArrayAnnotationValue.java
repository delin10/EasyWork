package nil.ed.easywork.source.obj.struct.anno;

import com.sun.tools.javac.code.TypeTag;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Getter
public class ArrayAnnotationValue extends AbstractAnnotationValue {

    private final List<AbstractAnnotationValue> valueList = new LinkedList<>();

    public ArrayAnnotationValue(String name) {
        super(name);
    }

}
