package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;
import nil.ed.easywork.source.obj.type.BaseClass;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class ClassTreeProcessor extends AbstractConsumeTreeProcessor<BaseClass, ClassTree> {

    @SuppressWarnings("unchecked")
    public ClassTreeProcessor() {
        super(ConsumerSingleton.CLASS_TREE_PARSE_CONSUMER.getConsumer());
    }

    @Override
    public boolean support(Tree.Kind kind) {
        return Tree.Kind.CLASS.equals(kind);
    }

    @Override
    public boolean support(Object obj) {
        return true;
    }

    @Override
    public BaseClass process(ClassTree tree) {
        if (apply != null) {
            return apply.apply(tree);
        }
        return null;
    }
}
