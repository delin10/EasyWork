package nil.ed.easywork.source.obj.access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Prefix;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
@Setter
@AllArgsConstructor
public abstract class BaseAccessControl implements Prefix {

    protected AccessEnum access;

    protected boolean isFinal;

    protected boolean isStatic;

}
