package nil.ed.easywork.source.obj;

import nil.ed.easywork.util.StringBuilderUtils;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class LineOutput {

    private String indent = "    ";

    public String output(List<Line> lines) {
        StringBuilder builder = new StringBuilder();
        lines.forEach(line -> {
            StringBuilderUtils.repeat(builder, line.getIndent(), indent);
            StringBuilderUtils.appendLines(builder, line.getBuilder());
        });
        return builder.toString();
    }

}
