package nil.ed.easywork.source.obj.type;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    private final List<JavaType> generic = new LinkedList<>();

    public static JavaType create(String fullyName) {
        return new JavaType(fullyName);
    }

    public JavaType(String fullyName) {
        this.fullyName = fullyName;
        resolveFullyName();
    }

    public JavaType(String fullyName, JavaType generic) {
        this.fullyName = fullyName;
        this.generic.add(generic);
        resolveFullyName();
    }

    public String getSimpleTypeName() {
        if (CollectionUtils.isEmpty(generic)) {
            return name;
        }

        return name + "<" + generic.stream().map(JavaType::getSimpleTypeName).collect(Collectors.joining(", "))+ ">";
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
