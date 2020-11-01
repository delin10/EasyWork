package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.Tree;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public interface TreeProcessor<S, T extends Tree> {

    /**
     * 是否支持kind类型
     * @param kind tree类型
     * @return 是否支持
     */
    boolean support(Tree.Kind kind);

    /**
     * 是否支持该对象
     * @param obj 对象
     * @return 是否支持
     */
    boolean support(Object obj);

    /**
     * 处理语法树
     * @param tree 语法树
     */
    S process(T tree);

}
