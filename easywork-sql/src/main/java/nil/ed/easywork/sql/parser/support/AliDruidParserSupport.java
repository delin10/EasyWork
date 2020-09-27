package nil.ed.easywork.sql.parser.support;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlTableIndex;
import nil.ed.easywork.sql.obj.Index;
import nil.ed.easywork.sql.obj.NonUniqueIndex;
import nil.ed.easywork.sql.obj.PrimaryIndex;
import nil.ed.easywork.sql.obj.UniqueIndex;
import nil.ed.easywork.sql.utils.SqlUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class AliDruidParserSupport {

    public static String getExprValue(SQLExpr sqlExpr) {
        if (sqlExpr instanceof SQLIdentifierExpr) {
            return SqlUtils.unwrapSqlNaming(((SQLIdentifierExpr) sqlExpr).getName());
        } else if (sqlExpr instanceof SQLCharExpr) {
            return SqlUtils.unwrapSqlNaming(((SQLCharExpr) sqlExpr).getText());
        }
        throw new IllegalArgumentException("Not support expr type: " + sqlExpr.getClass());
    }

    public static Index parseIndex(SQLTableElement e) {
        Index index = null;
        if (e instanceof MySqlPrimaryKey) {
            MySqlPrimaryKey key = (MySqlPrimaryKey) e;
            List<SQLSelectOrderByItem> keyColumns = key.getColumns();
            if (keyColumns.size() > 1) {
                throw new IllegalArgumentException("不支持多于1个的主键");
            }
            SQLSelectOrderByItem item = keyColumns.get(0);
            String colName = parseSQLSelectOrderByItemToString(item);
            index = new PrimaryIndex("");
            index.addColumns(colName);
        } else if (e instanceof MySqlUnique) {
            MySqlUnique unique = (MySqlUnique) e;
            List<SQLSelectOrderByItem> keyColumns = unique.getColumns();
            index = new UniqueIndex("");
            batchParseSQLSelectOrderByItemToString(keyColumns).forEach(index::addColumns);
        } else if (e instanceof MySqlTableIndex) {
            MySqlTableIndex tableIndex = (MySqlTableIndex) e;
            index = new NonUniqueIndex("");
            batchParseSQLSelectOrderByItemToString(tableIndex.getColumns()).forEach(index::addColumns);
        } else if (e instanceof MySqlKey) {
            MySqlKey key = (MySqlKey) e;
            index = new NonUniqueIndex("");
            batchParseSQLSelectOrderByItemToString(key.getColumns()).forEach(index::addColumns);
        }
        return index;
    }

    public static List<String> batchParseSQLSelectOrderByItemToString(List<SQLSelectOrderByItem> items) {
        return items.stream()
                .map(AliDruidParserSupport::parseSQLSelectOrderByItemToString)
                .collect(Collectors.toList());
    }

    public static String parseSQLSelectOrderByItemToString(SQLSelectOrderByItem item) {
        return AliDruidParserSupport.getExprValue(item.getExpr());
    }

}
