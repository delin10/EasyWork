package nil.ed.easywork.source.obj.struct.anno;

import nil.ed.easywork.source.obj.NamedValue;

/**
 * @author lidelin.
 */
public class AbstractAnnotationValue implements NamedValue {

    protected final String name;

    public AbstractAnnotationValue(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
