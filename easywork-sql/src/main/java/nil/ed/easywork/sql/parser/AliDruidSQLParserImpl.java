package nil.ed.easywork.sql.parser;

import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import nil.ed.easywork.sql.obj.BaseSchemaObj;
import nil.ed.easywork.sql.obj.ColumnField;
import nil.ed.easywork.sql.obj.CreateTableSchemaObj;
import nil.ed.easywork.sql.obj.Index;
import nil.ed.easywork.sql.obj.PrimaryIndex;
import nil.ed.easywork.sql.parser.support.AliDruidParserSupport;
import nil.ed.easywork.sql.utils.SqlUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidelin.
 */
public class AliDruidSQLParserImpl implements ISQLParser {

    @Override
    public BaseSchemaObj parse(String sql) {
        MySqlCreateTableParser parser = new MySqlCreateTableParser(sql);
        MySqlCreateTableStatement statement = parser.parseCreateTable(true);
        List<SQLTableElement> tableElements = statement.getTableElementList();
        String tableName = SqlUtils.unwrapSqlNaming(statement.getName().getSimpleName());
        CreateTableSchemaObj createTableSchemaObj = new CreateTableSchemaObj(tableName);
        Map<String, ColumnField> colMap = new LinkedHashMap<>();
        tableElements.forEach(e -> {
            if (e instanceof SQLColumnDefinition) {
                SQLColumnDefinition def = (SQLColumnDefinition) e;
                String colName = SqlUtils.unwrapSqlNaming(def.getColumnName());
                String colType = def.getDataType().getName();
                String colComment = SqlUtils.unwrapTextNaming(((SQLCharExpr) def.getComment()).getText());
                ColumnField columnField = new ColumnField(colName, colType.toUpperCase(), false);
                columnField.setComment(colComment);
                colMap.put(colName, columnField);
            } else {
                Index index = AliDruidParserSupport.parseIndex(e);
                if (index != null) {
                    index.getIndexColNames()
                            .stream()
                            .map(colMap::get)
                            .forEach(index::addColumns);
                    createTableSchemaObj.getIndices().add(index);
                    if (index instanceof PrimaryIndex) {
                        String name = index.getIndexColNames().get(0);
                        colMap.get(name).setPrimary(true);
                    }
                }
            }
        });
        colMap.values().forEach(createTableSchemaObj::addColumnField);
        createTableSchemaObj.setComment(AliDruidParserSupport.getExprValue(statement.getComment()));
        return createTableSchemaObj;
    }
}
