package nil.ed.easywork.util.seg;

import java.util.List;

/**
 * @author delin10
 * @since 2019/10/24
 **/
public interface Segmenter {
    /**
     * 分词接口
     * @param text 源文本
     * @return 分词结果
     */
    List<String> seg(String text);
}
