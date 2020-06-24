package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;
import nil.ed.easywork.source.parser.processor.ClassTreeParseConsumer;
import nil.ed.easywork.source.parser.processor.FiledInfoParseConsumer;

import java.util.function.Function;

/**
 * @author delin10
 * @since 2020/5/28
 **/
enum  ConsumerSingleton {
    /**
     * consumers
     */
    CLASS_TREE_PARSE_CONSUMER(new ClassTreeParseConsumer()),
    FIELD_INFO_PARSE_CONSUMER(new FiledInfoParseConsumer());

    private Function consumer;

    ConsumerSingleton(Function consumer) {
        this.consumer = consumer;
    }

    @SuppressWarnings("unchecked")
    public Object apply(Tree tree) {
        return consumer.apply(tree);
    }

    public Function getConsumer() {
        return consumer;
    }

}
