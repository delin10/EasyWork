package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.struct.BaseMethod;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public interface StatementSupplier extends BiFunction<BaseMethod, Integer, List<Line>> {
}
