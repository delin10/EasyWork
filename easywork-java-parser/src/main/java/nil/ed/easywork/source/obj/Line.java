package nil.ed.easywork.source.obj;

import lombok.Getter;
import lombok.Setter;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
public class Line {

    private StringBuilder builder = new StringBuilder();

    private int indent;

    public Line(CharSequence text, int indent) {
        this.builder.append(text);
        this.indent = indent;
    }

    public Line tailAppend(CharSequence text) {
        builder.append(text);
        return this;
    }

    public Line headAppend(CharSequence text) {
        builder.insert(0, text);
        return this;
    }
}
