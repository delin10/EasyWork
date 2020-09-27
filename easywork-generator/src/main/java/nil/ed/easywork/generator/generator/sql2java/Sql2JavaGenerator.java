package nil.ed.easywork.generator.generator.sql2java;

import nil.ed.easywork.generator.config.Config;

import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public interface Sql2JavaGenerator {

    /**
     * 生成代码.
     *
     * @param config root config.
     * @param cxt 上下文.
     * @param template 模版.
     * @return 生成的结果.
     */
    List<Object> generate(Map<String, Object> cxt, String template, Config config);

}
