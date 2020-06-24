package nil.ed.easywork.sql.parser;

import nil.ed.easywork.sql.obj.BaseObj;

/**
 * @author delin10
 * @since 2020/5/19
 **/
public interface ISQLParser {

    /**
     * 解析SQL接口
     * @param sql SQL语句
     * @return 结果
     */
    BaseObj parse(String sql);

}
