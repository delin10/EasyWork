package nil.ed.easywork.sql.obj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Setter
@Getter
@ToString(callSuper = true)
public class Index {

    private String name;

    private boolean unique;

    private List<ColumnField> indexFields = new LinkedList<>();

    private List<String> indexColNames = new LinkedList<>();

    public Index(String name, boolean unique) {
        this.name = name;
        this.unique = unique;
    }

    public Index addColumns(ColumnField...fields) {
        Collections.addAll(indexFields, fields);
        return this;
    }

    public Index addColumns(String...fields) {
        Collections.addAll(indexColNames, fields);
        return this;
    }

}
