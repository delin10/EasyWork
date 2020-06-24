package nil.ed.easywork.source.obj.struct;

import nil.ed.easywork.source.obj.Line;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/3
 **/
public interface Statement {

    /**
     * 获取Java语句
     * @param level 层次
     * @return Java语句
     */
    List<Line> getStatement(int level);

}
