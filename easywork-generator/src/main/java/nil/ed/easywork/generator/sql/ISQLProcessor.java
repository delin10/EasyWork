package nil.ed.easywork.generator.sql;

import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.sql.obj.BaseObj;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public interface ISQLProcessor {

    /**
     * 处理SQL
     * @return 表对象列表
     */
    List<TableDetails> process();

}
