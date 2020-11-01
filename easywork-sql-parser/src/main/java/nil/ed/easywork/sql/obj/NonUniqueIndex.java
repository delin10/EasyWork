package nil.ed.easywork.sql.obj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lidelin.
 */
@Setter
@Getter
@ToString(callSuper = true)
public class NonUniqueIndex extends Index {

    public NonUniqueIndex(String name) {
        super(name, false);
    }

}
