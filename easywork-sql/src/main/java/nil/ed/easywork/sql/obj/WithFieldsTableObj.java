package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.ToString;
import nil.ed.easywork.sql.enums.OpEnums;
import nil.ed.easywork.sql.exception.MyException;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@ToString(callSuper = true)
public class WithFieldsTableObj extends BaseTableObj {

    private List<ColumnField> fields = new LinkedList<>();

    @Getter
    private ColumnField id;

    public WithFieldsTableObj(OpEnums op, String name) {
        super(op, name);
    }

    public ColumnField addNormalField(String name, String type) {
        ColumnField field = new ColumnField(name, type, false);
        fields.add(field);
        return field;
    }

    public ColumnField addPrimaryField(String name, String type) {
        ColumnField field = new ColumnField(name, type, true);
        id = field;
        fields.add(field);
        return field;
    }

    public List<ColumnField> getFields() {
        return fields;
    }
}
