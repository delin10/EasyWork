package nil.ed.easywork.sql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Getter
@AllArgsConstructor
public enum DbType {

    /**
     * 数据库类型
     */
    MYSQL("MySQL");

    private String name;


}
