package nil.ed.easywork.sql.obj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lidelin.
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UniqueIndex extends Index {

    public UniqueIndex(String name) {
        super(name, true);
    }


}
