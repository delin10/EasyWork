package nil.ed.easywork.sql.obj;

import com.sun.tools.javac.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nil.ed.easywork.sql.enums.OpEnums;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Setter
@Getter
@ToString(callSuper = true)
public class CreateTableSchemaObj extends WithFieldsTableSchemaObj {

    private String comment;

    private List<Index> indices = new LinkedList<>();

    public CreateTableSchemaObj(String name) {
        super(OpEnums.CREATE_TABLE, name);
    }

}
