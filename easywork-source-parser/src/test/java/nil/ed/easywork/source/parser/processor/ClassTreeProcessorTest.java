package nil.ed.easywork.source.parser.processor;

import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.struct.BaseClass;
import nil.ed.easywork.source.parser.JavaParser;
import nil.ed.easywork.source.parser.JavacParserImpl;
import nil.ed.easywork.source.parser.visitor.GenericScanner;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ClassTreeProcessorTest {
    @Test
    public void process() throws IOException {
        JavaParser<JCTree.JCCompilationUnit> parser = new JavacParserImpl();
        String dir = "D:\\000delin\\workspace\\Easywork\\easywork-sql\\src\\main\\java\\nil\\ed\\easywork\\sql\\parser";
        List<String> lines = IOUtils.readLines(Files.newInputStream(Paths.get(dir, "ShardingsphereSQLParserImpl.java")), StandardCharsets.UTF_8);
        String src = String.join("\n", lines);
        JCTree.JCCompilationUnit unit = parser.parse(src);
        GenericScanner scanner = new GenericScanner(unit);
        BaseClass clazz = scanner.visitCompilationUnit(unit, null);
        System.out.println(clazz);
    }
}
