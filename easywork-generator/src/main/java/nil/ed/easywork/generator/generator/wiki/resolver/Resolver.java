package nil.ed.easywork.generator.generator.wiki.resolver;

import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import org.jsoup.select.Elements;

/**
 * @author lidelin.
 */
public interface Resolver {

    /**
     * 支持的tag.
     * @return tag.
     */
    String selector();

    /**
     * 解析.
     * @param es 元素.
     * @return 结果.
     */
    ResolveResult resolve(Elements es);

}
