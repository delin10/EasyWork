package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.struct.BaseMethod;

import java.util.Collections;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public class EmptyBodySupplier implements StatementSupplier {
    @Override
    public List<Line> apply(BaseMethod method, Integer value) {
        return Collections.emptyList();
    }
}
