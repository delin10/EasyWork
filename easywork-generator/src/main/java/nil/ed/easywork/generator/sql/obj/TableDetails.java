package nil.ed.easywork.generator.sql.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import nil.ed.easywork.sql.obj.CreateTableSchemaObj;

import java.util.List;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@AllArgsConstructor
@Getter
@ToString
public class TableDetails {

    private final String call;

    private final CreateTableSchemaObj tableObj;

    private final List<ColumnDetails> columnDetails;

    private final String entityDesc;

}
