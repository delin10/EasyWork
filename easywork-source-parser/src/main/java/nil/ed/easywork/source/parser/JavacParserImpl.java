package nil.ed.easywork.source.parser;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class JavacParserImpl implements JavaParser<JCTree.JCCompilationUnit> {

    private static ParserFactory factory;

    static {
        Context context = new Context();
        JavacFileManager.preRegister(context);
        factory = ParserFactory.instance(context);
    }

    @Override
    public JCTree.JCCompilationUnit parse(CharSequence source) {
        return factory.newParser(source, false, false, false).parseCompilationUnit();
    }

}
