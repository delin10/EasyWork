package nil.ed.easywork.sql.obj;

import lombok.AllArgsConstructor;
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
public abstract class BaseTableObj extends BaseObj {

    private String name;

    public BaseTableObj(OpEnums op, String name) {
        super(op);
        this.name = name;
    }
}
