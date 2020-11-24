package nil.ed.easywork.generator.generator.wiki.bean;

import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;

/**
 * @author lidelin.
 */
public interface ResolveResult {

    /**
     * 注册.
     */
    default void register() {
        ResolveContext.CXT.put(getKey(), getResult());
    }

    /**
     * 获取结果.
     * @return 结果.
     */
    Object getResult();
    /**
     * 获取context注册key.
     * @return 注册key.
     */
    String getKey();

    /**
     * 获取父结点.
     * @return 父节点.
     */
    default ResolveResult getParent() {
        return null;
    }

}
