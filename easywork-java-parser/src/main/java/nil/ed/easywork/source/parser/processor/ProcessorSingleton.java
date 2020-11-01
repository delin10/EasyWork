package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;
import nil.ed.easywork.source.parser.processor.ClassTreeProcessor;
import nil.ed.easywork.source.parser.processor.FieldTreeProcessor;
import nil.ed.easywork.source.parser.processor.TreeProcessor;

/**
 * @author delin10
 * @since 2020/5/28
 **/
@SuppressWarnings("rawtypes")
public enum  ProcessorSingleton {
    /**
     * Tree processor singleton
     */
    CLASS_INFO_PROCESSOR(new ClassTreeProcessor()),
    FIELD_TREE_PROCESSOR(new FieldTreeProcessor()),
    METHOD_TREE_PROCESSOR(new MethodTreeProcessor()),
    ;

    public static Object NON_SUPPORT_OBJ = new Object();
    private final TreeProcessor processor;

    ProcessorSingleton(TreeProcessor processor) {
        this.processor = processor;
    }

    @SuppressWarnings("unchecked")
    public Object apply(Tree tree) {
        if (processor.support(tree) && processor.support(tree.getKind())) {
            return processor.process(tree);
        }
        return NON_SUPPORT_OBJ;
    }

}
