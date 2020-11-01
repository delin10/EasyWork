package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.struct.BaseField;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class FieldTreeProcessor extends AbstractConsumeTreeProcessor<BaseField, JCTree.JCVariableDecl>{


    public FieldTreeProcessor() {
        super(new FiledInfoParseConsumer());
    }

    @Override
    public boolean support(Tree.Kind kind) {
        return Tree.Kind.VARIABLE.equals(kind);
    }

    @Override
    public boolean support(Object obj) {
        return true;
}

    @Override
    public BaseField process(JCTree.JCVariableDecl tree) {
        if (apply != null) {
            return apply.apply(tree);
        }
        return null;
    }

}
