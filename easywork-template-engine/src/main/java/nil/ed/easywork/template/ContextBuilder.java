package nil.ed.easywork.template;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public interface ContextBuilder<C> {

    /**
     * 设置值
     * @param name 值名称
     * @param obj 值
     * @return 级联对象
     */
    ContextBuilder<C> put(String name, Object obj);

    /**
     * 获取context
     * @return context
     */
    C getContext();

}
