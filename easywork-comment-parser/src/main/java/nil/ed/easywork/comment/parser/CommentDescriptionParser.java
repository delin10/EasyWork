package nil.ed.easywork.comment.parser;

import com.google.common.collect.Sets;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    private final Character terminateChar = ']';
    private final Set<Character> startCharSet = Sets.newHashSet('[', '(', ',');
    private final Set<Character> endCharSet = Sets.newHashSet(']', ')');

    public List<CommentDescription> parse(String comment) {
        if (StringUtils.isBlank(comment)) {
            return Collections.emptyList();
        }
        int i = 0;
        String[] arr = comment.split("&");
        List<CommentDescription> commentDescriptions = new LinkedList<>();
        for (String one : arr) {
            System.out.println(one);
            CommentDescription description = new CommentDescription();
            parseOneDescription(one, description, 0);
            commentDescriptions.add(description);
        }
        return commentDescriptions;
    }

    private void parseOneDescription(String str, CommentDescription description, int i) {
        if (StringUtils.isBlank(str)) {
            return;
        }
        String identifier = getOneIdentifier(str, i, startCharSet);
        description.setFunc(FunctionEnum.findByName(identifier));
        i += identifier.length() + 1;
        while (i < str.length() && str.charAt(i) != terminateChar) {
            String name = getOneIdentifier(str, i, startCharSet);
            if (StringUtils.isBlank(name)) {
                i++;
                continue;
            }
            i += identifier.length() + 1;
            String value = getOneIdentifier(str, i, endCharSet);
            if (StringUtils.isBlank(value)) {
                i++;
                continue;
            }
            i += value.length() + 1;
            System.out.println("==" + name);
            System.out.println(value);
            set(description, name, value);
        }
    }

    private String getOneIdentifier(String str, int cur, Set<Character> utilChar) {
        int start = cur;
        while (cur < str.length() && !utilChar.contains(str.charAt(cur++))) { }
        return str.substring(start, cur == str.length() ? cur : cur - 1);
    }

    private void set(Object bean, String name, String value) {
        try {
            BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
