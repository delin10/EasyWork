package nil.ed.easywork.source.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * @author delin10
 * @since 2020/6/2
 **/
@Getter
@Setter
@ToString
public class MappedJavaObject {

    private String name;

    private String pkg;

    private SortedSet<String> imports = new TreeSet<>();

    private List<MappedJavaField> fields = new LinkedList<>();

}
