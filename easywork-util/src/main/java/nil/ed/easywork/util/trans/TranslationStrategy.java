package nil.ed.easywork.util.trans;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public interface TranslationStrategy {

    /**
     * 变量命名
     * @param chineseName 中文名称
     * @return 命名候选列表
     */
    List<String> trans(String chineseName);

}
