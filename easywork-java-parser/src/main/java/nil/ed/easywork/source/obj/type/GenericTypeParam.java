package nil.ed.easywork.source.obj.type;

/**
 * @author lidelin.
 */
public class GenericTypeParam implements TypeParam {

    private final String name;

    public GenericTypeParam(String name) {
        this.name = name;
    }

    @Override
    public boolean isGeneric() {
        return true;
    }
}
