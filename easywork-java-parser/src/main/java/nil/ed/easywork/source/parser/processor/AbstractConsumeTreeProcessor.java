package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;

import java.util.function.Function;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public abstract class AbstractConsumeTreeProcessor<S, T extends Tree> implements TreeProcessor<S, T> {

    protected Function<T, S> apply;

    public AbstractConsumeTreeProcessor(Function<T, S> apply) {
        this.apply = apply;
    }

}
