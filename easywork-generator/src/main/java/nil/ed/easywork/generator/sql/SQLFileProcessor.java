package nil.ed.easywork.generator.sql;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.comment.parser.CommentDescriptionParser;
import nil.ed.easywork.generator.sql.obj.ColumnDetails;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.type.ColTypeTransformer;
import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.type.impl.TypeMapper;
import nil.ed.easywork.sql.enums.DbType;
import nil.ed.easywork.sql.obj.BaseObj;
import nil.ed.easywork.sql.obj.CreateTableObj;
import nil.ed.easywork.sql.parser.ISQLParser;
import nil.ed.easywork.sql.parser.ShardingsphereSQLParserImpl;
import nil.ed.easywork.util.Utils;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@Slf4j
public class SQLFileProcessor implements ISQLProcessor{

    private String sqlPath;

    private ISQLParser sqlParser;

    private CommentDescriptionParser commentDescriptionParser;

    private ITypeMapper mapper;

    private ColTypeTransformer typeTransformer;

    public SQLFileProcessor(String sqlPath, ISQLParser sqlParser, CommentDescriptionParser commentDescriptionParser,
                            ITypeMapper mapper, ColTypeTransformer typeTransformer) {
        this.sqlPath = sqlPath;
        this.sqlParser = sqlParser;
        this.commentDescriptionParser = commentDescriptionParser;
        this.mapper = mapper;
        this.typeTransformer = typeTransformer;
    }

    public SQLFileProcessor(String sqlPath, ITypeMapper mapper) {
        this(sqlPath, mapper, null);
    }

    public SQLFileProcessor(String sqlPath, ITypeMapper mapper, ColTypeTransformer typeTransformer) {
        this(sqlPath, new ShardingsphereSQLParserImpl(DbType.MYSQL), new CommentDescriptionParser(), mapper, typeTransformer);
    }

    @Override
    public List<TableDetails> process() {
        List<String> ddls = readSQLAndSplit();
        if (CollectionUtils.isEmpty(ddls)) {
            return Collections.emptyList();
        }
        List<TableDetails> tablesObjs = new LinkedList<>();
        ddls.forEach(ddl -> {
            BaseObj obj = sqlParser.parse(ddl);
            if (obj instanceof CreateTableObj) {
                List<ColumnDetails> columnDetails = new LinkedList<>();
                CreateTableObj tableObj = (CreateTableObj) obj;
                tableObj.getFields().forEach(columnField -> {
                    columnField.setType(transformType(columnField.getType()));
                    ColumnDetails field = new ColumnDetails(columnField);
                    String commentDescriptionText = getCommentDescription(columnField.getComment());
                    List<CommentDescription> descriptions = Optional.ofNullable(commentDescriptionText)
                            .map(commentDescriptionParser::parse)
                            .orElse(Collections.emptyList());
                    String camelColName = NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(columnField.getName());
                    String javaType = mapper.map(columnField.getType());
                    descriptions.forEach(desc -> {
                        if (StringUtils.isBlank(desc.getName())) {
                            desc.setName(camelColName);
                        }
                        if (StringUtils.isBlank(desc.getType())) {
                            desc.setType(javaType);
                        }
                    });
                    descriptions.stream()
                            .peek(desc -> desc.setOriginName(columnField.getName()))
                            .forEach(description -> field.getDescriptionMap().put(description.getFunc(), description));
                    columnDetails.add(field);
                });
                TableDetails tableDetails = new TableDetails(tableObj, columnDetails);
                tablesObjs.add(tableDetails);
            }
        });
        return tablesObjs;
    }

    private String transformType(String type) {
       if (typeTransformer != null) {
           return typeTransformer.transform(type);
       }
       return type;
    }

    private static final Pattern pattern = Pattern.compile("[\\s\\S]*?\\{\\{(.*?)}}");
    private String getCommentDescription(String comment) {
        if (StringUtils.isBlank(comment)) {
            return null;
        }

        Matcher matcher = pattern.matcher(comment);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private List<String> readSQLAndSplit() {
        String sql = "";
        try {
            sql = Utils.readText(sqlPath, StandardCharsets.UTF_8);
            return Arrays.stream(sql.split(";\\s"))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
        } catch (IOException ignore) {
            log.error("Read sql script error: {}", sqlPath, ignore);
        }
        return Collections.emptyList();
    }
}
