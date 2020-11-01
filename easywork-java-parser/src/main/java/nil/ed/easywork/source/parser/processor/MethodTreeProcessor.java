package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.struct.BaseMethod;

import java.util.function.Function;

/**
 * @author lidelin.
 */
@SuppressWarnings("unchecked")
public class MethodTreeProcessor  extends AbstractConsumeTreeProcessor<BaseMethod, JCTree.JCMethodDecl> {

    public MethodTreeProcessor() {
        this(ConsumerSingleton.METHOD_PARSE_CONSUMER.getConsumer());
    }

    public MethodTreeProcessor(Function<JCTree.JCMethodDecl, BaseMethod> apply) {
        super(apply);
    }

    @Override
    public boolean support(Tree.Kind kind) {
        return true;
    }

    @Override
    public boolean support(Object obj) {
        return JCTree.JCMethodDecl.class.isAssignableFrom(obj.getClass());
    }

    @Override
    public BaseMethod process(JCTree.JCMethodDecl tree) {
        if (apply != null) {
            return apply.apply(tree);
        }
        return null;
    }
}
