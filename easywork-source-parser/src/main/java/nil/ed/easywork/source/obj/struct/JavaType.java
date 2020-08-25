package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.ToString;

/**
 * @author delin10
 * @since 2020/6/2
 **/
@ToString
@Getter
public class JavaType {

    private String pkg;

    private String name;

    private final String fullyName;

    private JavaType generic;

    public static JavaType create(String fullyName) {
        return new JavaType(fullyName);
    }

    public JavaType(String fullyName) {
        this.fullyName = fullyName;
        resolveFullyName();
    }

    public JavaType(String fullyName, JavaType generic) {
        this.fullyName = fullyName;
        this.generic = generic;
        resolveFullyName();
    }

    public String getSimpleTypeName() {
        if (generic == null) {
            return name;
        }

        return name + "<" + generic.getSimpleTypeName() + ">";
    }

    private void resolveFullyName() {
        int dotIndex = fullyName.lastIndexOf('.');
        if (dotIndex < 0) {
            pkg = "";
            name = fullyName;
        } else {
            pkg = fullyName.substring(0, dotIndex);
            name = fullyName.substring(dotIndex + 1);
        }
    }
}
