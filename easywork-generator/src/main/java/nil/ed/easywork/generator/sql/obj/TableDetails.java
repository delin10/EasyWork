package nil.ed.easywork.generator.sql.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nil.ed.easywork.sql.obj.CreateTableObj;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@AllArgsConstructor
@Getter
@ToString
public class TableDetails {

    private CreateTableObj tableObj;

    private List<ColumnDetails> columnDetails;

}
