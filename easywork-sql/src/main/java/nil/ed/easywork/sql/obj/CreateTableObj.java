package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nil.ed.easywork.sql.enums.OpEnums;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Setter
@Getter
@ToString(callSuper = true)
public class CreateTableObj extends WithFieldsTableObj {

    private String comment;

    public CreateTableObj(String name) {
        super(OpEnums.CREATE_TABLE, name);
    }

}
