package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.ToString;
import nil.ed.easywork.sql.enums.OpEnums;

import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@ToString(callSuper = true)
public class WithFieldsTableSchemaObj extends BaseTableSchemaObj {

    private List<ColumnField> fields = new LinkedList<>();

    @Getter
    private ColumnField id;

    public WithFieldsTableSchemaObj(OpEnums op, String name) {
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

    public void addColumnField(ColumnField...fields) {
        for (ColumnField f : fields) {
            if (f.isPrimary()) {
                this.id = f;
            }
            this.fields.add(f);
        }
    }

    public List<ColumnField> getFields() {
        return fields;
    }
}
