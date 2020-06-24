package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Getter
@Setter
@ToString(callSuper = true)
public class ColumnField implements Comparable<ColumnField>{

    private String name;

    private String comment;

    private String type;

    private boolean primary;

    public ColumnField(String name, String type, boolean primary) {
        this.name = name;
        this.type = type;
        this.primary = primary;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ColumnField) {
            ColumnField f = (ColumnField) obj;
            return f.name != null && f.name.equals(name);
        }
        return false;
    }

    @Override
    public int compareTo(ColumnField o) {
        if (o == null) {
            return -1;
        }
        if (this.primary) {
            return 1;
        }
        return this.name.compareTo(o.name);
    }
}
