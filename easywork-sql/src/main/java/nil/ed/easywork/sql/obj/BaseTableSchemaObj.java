package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nil.ed.easywork.sql.enums.OpEnums;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Getter
@Setter
@ToString(callSuper = true)
public abstract class BaseTableSchemaObj extends BaseSchemaObj {

    private String name;

    public BaseTableSchemaObj(OpEnums op, String name) {
        super(op);
        this.name = name;
    }
}
