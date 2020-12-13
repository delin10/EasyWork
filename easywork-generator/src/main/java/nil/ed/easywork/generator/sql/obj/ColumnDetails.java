package nil.ed.easywork.generator.sql.obj;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.sql.obj.ColumnField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@Getter
public class ColumnDetails  {

    private ColumnField field;

    private Map<FunctionEnum, List<CommentDescription>> descriptionMap = new HashMap<>();

    public ColumnDetails(ColumnField field) {
        this.field = field;
    }
}
