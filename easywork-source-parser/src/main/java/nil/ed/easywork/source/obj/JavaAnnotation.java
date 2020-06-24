package nil.ed.easywork.source.obj;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
public class JavaAnnotation {

    private String name;

    private Map<String, String> params;

    public JavaAnnotation(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "@" +
                name +
                params.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + " = " + entry.getValue())
                        .collect(Collectors.joining(",", "(", ")"));

    }
}
