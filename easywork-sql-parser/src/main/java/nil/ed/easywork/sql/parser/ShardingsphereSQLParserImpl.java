package nil.ed.easywork.sql.parser;

import lombok.Getter;
import nil.ed.easywork.sql.enums.DbType;
import nil.ed.easywork.sql.obj.BaseSchemaObj;
import nil.ed.easywork.sql.obj.ColumnField;
import nil.ed.easywork.sql.obj.CreateTableSchemaObj;
import org.apache.shardingsphere.sql.parser.SQLParserEngineFactory;
import org.apache.shardingsphere.sql.parser.sql.segment.ddl.column.ColumnDefinitionSegment;
import org.apache.shardingsphere.sql.parser.sql.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.CreateTableStatement;
import org.apache.shardingsphere.sql.parser.sql.value.identifier.IdentifierValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/5/19
 **/
@Deprecated
public class ShardingsphereSQLParserImpl implements ISQLParser {

    @Getter
    private final DbType dbType;

    public ShardingsphereSQLParserImpl(DbType dbType) {
        // todo assert null error
        this.dbType = dbType;
    }

    @Override
    public BaseSchemaObj parse(String sql) {

        SQLStatement actual = SQLParserEngineFactory.getSQLParserEngine(dbType.getName()).parse(sql, false);
        if (actual instanceof CreateTableStatement) {
            CreateTableStatement createTableStatement = (CreateTableStatement) actual;
            String tableName = createTableStatement.getTable().getTableName().getIdentifier().getValue();
            CreateTableSchemaObj tableObj = new CreateTableSchemaObj(tableName);
            Set<String> primaryKeys = new HashSet<>();
            createTableStatement.getConstraintDefinitions().forEach(cd -> {
                if (!cd.getPrimaryKeyColumns().isEmpty()) {
                    List<String> keys = cd.getPrimaryKeyColumns().stream()
                            .map(ColumnSegment::getIdentifier)
                            .map(IdentifierValue::getValue)
                            .collect(Collectors.toList());
                    primaryKeys.addAll(keys);
                }
            });
            for (ColumnDefinitionSegment col : createTableStatement.getColumnDefinitions()) {
                String colName = col.getColumnName().getIdentifier().getValue();
                String typeName = col.getDataType().getDataTypeName();
                ColumnField field;
                if (col.isPrimaryKey() || primaryKeys.contains(colName)) {
                    field = tableObj.addPrimaryField(colName, typeName);
                } else {
                    field = tableObj.addNormalField(colName, typeName);
                }
                String colDefSql = sql.substring(col.getStartIndex(), col.getStopIndex() + 1);
                field.setComment(parseComment(colDefSql));
            }
            tableObj.setComment(parseComment(sql));
            if (tableObj.getId() == null) {
                tableObj.addPrimaryField("id", "bigint");
            }
            return tableObj;
        }
        return null;
    }

    /**
    .* 匹配除\n之外的所有单字符
     */
    private static final String COL_COMMENT_PATTERN_STR = "[\\S\\s]+\\s*comment\\s*['\"](.*?)['\"]\\s*[,;]?";
    private static final Pattern COL_COMMENT_PATTERN = Pattern.compile(COL_COMMENT_PATTERN_STR, Pattern.CASE_INSENSITIVE);
    public String parseComment(String oneDef) {
        Matcher matcher = COL_COMMENT_PATTERN.matcher(oneDef);

        if (!matcher.matches()) {
            return "";
        }

        return matcher.group(1);
    }

}
