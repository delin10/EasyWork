package nil.ed.easywork.source.obj;

import lombok.Getter;
import nil.ed.easywork.source.obj.struct.anno.AbstractAnnotationValue;
import nil.ed.easywork.source.obj.struct.anno.StringAnnotationValue;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.util.FlowUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
public class JavaAnnotation {

    private final JavaType type;

    private List<AbstractAnnotationValue> attributes;

    public JavaAnnotation(JavaType type) {
        this.type = type;
    }

    public JavaAnnotation putValue(AbstractAnnotationValue value) {
        attributes().add(value);
        return this;
    }

    public Map<String, AbstractAnnotationValue> attributeMap() {
        return attributes().stream()
                .collect(Collectors.toMap(AbstractAnnotationValue::name, Function.identity()));
    }

    public List<AbstractAnnotationValue> attributes() {
        if (attributes == null) {
            this.attributes = new LinkedList<>();
        }
        return attributes;
    }

    public Optional<AbstractAnnotationValue> attribute(String name) {
        return Optional.ofNullable(attributeMap().get(name));
    }

    public Optional<String> stringValue(String name) {
        return attribute(name)
                .map(StringAnnotationValue.class::cast)
                .map(StringAnnotationValue::value);
    }

    public Optional<Integer> intValue(String name) {
        return attribute(name)
                .map(StringAnnotationValue.class::cast)
                .map(StringAnnotationValue::value)
                .map(NumberUtils::toInt);
    }

    public Optional<Double> doubleValue(String name) {
        return attribute(name)
                .map(StringAnnotationValue.class::cast)
                .map(StringAnnotationValue::value)
                .map(NumberUtils::toDouble );
    }

}
