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
@AllArgsConstructor
@ToString
public abstract class BaseSchemaObj {

    private OpEnums op;

}
