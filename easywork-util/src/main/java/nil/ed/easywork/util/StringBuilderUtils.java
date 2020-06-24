package nil.ed.easywork.util;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class StringBuilderUtils {

    public static void appendAll(StringBuilder builder, CharSequence open, CharSequence close, CharSequence...args) {
        for (CharSequence arg : args) {
            builder.append(open);
            builder.append(arg);
            builder.append(close);
        }
    }

    public static void appendLines(StringBuilder builder, CharSequence...args) {
        appendAll(builder, "", System.lineSeparator(), args);
    }

    public static void appendSeparateBySpace(StringBuilder builder, CharSequence...args) {
        appendAll(builder, "", " ", args);
        if (args.length > 0) {
            builder.delete(builder.length() - 1,  builder.length());
        }
    }

    public static CharSequence INDENT = "    ";

    public static void appendIndentLines(StringBuilder builder, int level, CharSequence...args) {
        CharSequence indent = repeat(new StringBuilder(), level, INDENT).toString();
        CharSequence[] processed = new CharSequence[args.length];
        for (int i = 0; i < args.length; ++i) {
            StringBuilder indentBuilder = new StringBuilder();
            CharSequence[] lines = args[i].toString().split(System.lineSeparator());
            appendAll(indentBuilder, indent, System.lineSeparator(), lines);
            processed[i] = indentBuilder.toString();
        }
        appendAll(builder, "", System.lineSeparator(), processed);
    }

    public static StringBuilder repeat(StringBuilder builder, int count, CharSequence str) {
        for (int i = 0; i < count; ++i) {
            builder.append(str);
        }
        return builder;
    }

    public static void addString(List<StringBuilder> ls, CharSequence str) {
        ls.add(new StringBuilder(str));
    }

}
