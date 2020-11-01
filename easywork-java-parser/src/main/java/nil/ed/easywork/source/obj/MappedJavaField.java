package nil.ed.easywork.source.obj;

import lombok.Getter;
import lombok.ToString;
import nil.ed.easywork.source.obj.type.JavaType;

/**
 * @author delin10
 * @since 2020/6/2
 **/
@Getter
@ToString
public class MappedJavaField {

    private String name;

    private JavaType type;

    private boolean primary;

    public MappedJavaField(String name, JavaType type, boolean primary) {
        this.name = name;
        this.type = type;
        this.primary = primary;
    }
}
