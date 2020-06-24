package nil.ed.easywork.comment.parser;

import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;

import java.util.LinkedList;
import java.util.List;

/**
 * 注释解析器
 * 语法： func[func1(identifier), func2(identifier)]
 * list[name(alias),type(String)]
 * @author delin10
 * @since 2020/5/28
 *
 * fixme 使用antrr来设计解析comment
 **/
public class CommentDescriptionParser {

    public List<CommentDescription> parse(String comment) {
        int i = 0;
        List<CommentDescription> commentDescriptions = new LinkedList<>();
        while (i < comment.length()) {
            CommentDescription description = new CommentDescription();
            i = parseOneDescription(comment, description, i);
            if (i < 0) {
                throw new IllegalArgumentException("出错");
            }
            commentDescriptions.add(description);
        }
        return commentDescriptions;
    }

    private int parseOneDescription(String str, CommentDescription description, int start) {
        int end = start = skipWhiteSpace(str, start);
        if (isTerminate(str, start)) {
            return str.length();
        }

        for (; end < str.length(); ++end) {
            char ch = str.charAt(end);
            if (ch == '[') {
                break;
            }
        }
        String funcName = str.substring(start, end).trim();
        FunctionEnum functionEnum = FunctionEnum.findByName(funcName);
        if (functionEnum == null) {
            return -1;
        }
        description.setFunc(functionEnum);
        start = end = end + 1;
        if (isTerminate(str, start)) {
            return str.length();
        }
        end = skipWhiteSpace(str, end);
        if (isTerminate(str, start)) {
            return -1;
        }
        if (str.charAt(end) == ']') {
            return str.length();
        }
        start = end = end + 1;
        while (!isTerminate(str, end)) {
            while (end < str.length() && str.charAt(end) != '(') {
                end++;
            }
            if (isTerminate(str, end)) {
                return -1;
            }
            String func = str.substring(start, end).trim();
            start = end = end + 1;
            while (end < str.length() && str.charAt(end) != ')') {
                end++;
            }
            if (isTerminate(str, end)) {
                return -1;
            }
            String identifier = str.substring(start, end);
            if ("type".equals(func)) {
                description.setType(identifier);
            } else if ("name".equals(func)) {
                description.setName(identifier);
            }
            start = end = end + 1;
            if (isTerminate(str, end)) {
                return -1;
            }

            if (str.charAt(end) == ']') {
                return start + 1;
            }

            if (str.charAt(end) == ',') {
                start = end = end + 1;
            }
        }
        return str.length();
    }

    private int skipWhiteSpace(String str, int start) {
        while (start < str.length() && Character.isWhitespace(str.charAt(start))) {
            start++;
        }
        return Math.min(start, str.length());
    }

    private boolean isTerminate(String str, int start) {
        return str.length() <= start;
    }

}
