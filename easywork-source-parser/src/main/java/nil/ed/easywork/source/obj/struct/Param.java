package nil.ed.easywork.source.obj.struct;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.JavaAnnotation;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class Param implements Comparable<Param> {

    private String name;

    private JavaType type;

    private Set<JavaAnnotation> annotationSet = new LinkedHashSet<>();

    public Param(String name, JavaType type) {
        this.name = name;
        this.type = type;
    }

    public Param() { }

    public String getDeclaration() {
        String annoPrefix = annotationSet.stream()
                .map(JavaAnnotation::toString)
                .collect(Collectors.joining(" "));
        return annoPrefix + " " + type.getSimpleTypeName() + " " + name;
    }

    @Override
    public String toString() {
        return getDeclaration();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Param) {
            Param p = (Param) obj;
            return this.name.equals(p.name);
        }
        return false;
    }

    @Override
    public int compareTo(Param o) {
        return name.compareTo(o.getName());
    }
}
