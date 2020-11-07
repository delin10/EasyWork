package nil.ed.easywork.generator.generator.sql2java.listener;

import nil.ed.easywork.generator.config.Config;

import java.util.Map;

/**
 * 监听模版渲染事件.
 *
 * @author lidelin.
 */
public interface TemplateRenderListener {

    /**
     * 模版渲染前调用.
     * @param context 渲染上下文.
     * @param template 模版.
     * @param config 全局配置.
     */
    void beforeRender(Map<String, Object> context, String template, Config config);

    /**
     * 模版渲染后调用.
     * @param context 渲染上下文.
     * @param template 模版.
     * @param config 全局配置.
     * @param renderResult 渲染结果.
     * @return null-保留原值; 返回字符串替换渲染结果.
     */
    String afterRender(Map<String, Object> context, String template, Config config, String renderResult);

}
