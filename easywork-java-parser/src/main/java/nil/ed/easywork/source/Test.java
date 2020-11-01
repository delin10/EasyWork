package nil.ed.easywork.source;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.Parser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class Test {
    public static void main(String[] args) throws IOException {
        String dir = "D:\\000delin\\workspace\\Easywork\\easywork-sql\\src\\main\\java\\nil\\ed\\easywork\\sql\\parser";
        List<String> lines = IOUtils.readLines(Files.newInputStream(Paths.get(dir, "ShardingsphereSQLParserImpl.java")), StandardCharsets.UTF_8);
        String src = String.join("\n", lines);
        Context context = new Context();
        JavacFileManager.preRegister(context);
        ParserFactory factory = ParserFactory.instance(context);
        Parser parser = factory.newParser(src, true, false, true);
        JCTree.JCCompilationUnit unit = parser.parseCompilationUnit();
        List<String> ls = new LinkedList<>();
        unit.defs.forEach(tree -> new MethodScanner().visitCompilationUnit(unit, ls));
        System.out.println(ls);
    }
    static class MethodScanner extends
            TreeScanner<List<String>, List<String>> {
        @Override
        public List<String> visitMethod(MethodTree node, List<String> p) {
            p.add(node.getName().toString());
            return p;
        }

        @Override
        public List<String> visitClass(ClassTree classTree, List<String> strings) {
            classTree.getMembers().forEach(tree -> {
                System.out.println(tree);
                // 变量是VARIABLE
                // METHOD
                if (tree.getKind().equals(Tree.Kind.VARIABLE)) {
                    JCTree.JCVariableDecl node = (JCTree.JCVariableDecl) tree;
                }
            });
            return strings;
        }
    }

}
