package nil.ed.easywork.template;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public interface ITemplateEngineAdapter<T> {

    /**
     * 处理
     *
     * @param template 模板
     * @param context context
     * @return 结果
     */
    String process(String template, T context);

    /**
     * 获得Context构建器
     * @return Context构建器
     */
    ContextBuilder<T> getContextBuilder();

}
