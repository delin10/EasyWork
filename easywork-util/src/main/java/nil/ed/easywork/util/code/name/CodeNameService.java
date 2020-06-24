package nil.ed.easywork.util.code.name;

import java.util.List;
import java.util.Set;

/**
 * @author delin10
 * @since 2020/6/24
 **/
public interface CodeNameService {

    /**
     * 命名服务
     * @param src 源文本
     * @return 名称列表
     */
    Set<String> name(String src);

}
