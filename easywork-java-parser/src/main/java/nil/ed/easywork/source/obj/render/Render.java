package nil.ed.easywork.source.obj.render;

import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.struct.Statement;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public interface Render<E extends Statement> {



    /**
     * 生成Java语句
     * @param e 语句对象
     * @param level 代码层次
     * @return 语句列表
     */
    List<Line> render(E e, int level);

}
