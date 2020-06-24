package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;
import nil.ed.easywork.source.obj.struct.BaseClass;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.parser.ProcessorSingleton;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class ClassTreeProcessor<S> extends AbstractConsumeTreeProcessor<BaseClass, ClassTree> {

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
            BaseClass clazz = apply.apply(tree);
            tree.getMembers().forEach(mem -> {
                Object result = ProcessorSingleton.FIELD_TREE_PROCESSOR.apply(mem);
                if (result != null && result != ProcessorSingleton.NON_SUPPORT_OBJ) {
                    clazz.getFields().add((BaseField) result);
                }
            });
            return clazz;
        }
        return null;
    }
}
