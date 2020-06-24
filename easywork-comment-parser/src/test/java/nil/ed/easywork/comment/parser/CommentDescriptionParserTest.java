package nil.ed.easywork.comment.parser;

import nil.ed.easywork.comment.obj.CommentDescription;
import org.junit.Test;

import java.util.List;
import java.util.Timer;

import static org.junit.Assert.*;

public class CommentDescriptionParserTest {

    @Test
    public void parse() {
        CommentDescriptionParser parser = new CommentDescriptionParser();
        List<CommentDescription> description = parser.parse("list[name(xxx),type(xxx)]");
        System.out.println(description);
        description = parser.parse("list[name(xxx)]");
        System.out.println(description);
        description = parser.parse("list[type(xxx)]");
        System.out.println(description);
        description = parser.parse("list[]");
        System.out.println(description);
        description = parser.parse("list");
        System.out.println(description);
    }
}