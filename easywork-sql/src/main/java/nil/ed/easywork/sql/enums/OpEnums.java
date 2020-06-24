package nil.ed.easywork.sql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Getter
@AllArgsConstructor
public enum OpEnums {
    /**
     * SQL语句的类型
     */
    CREATE_TABLE("CREATE", "创建表语句");
    private String op;

    private String desc;

}
