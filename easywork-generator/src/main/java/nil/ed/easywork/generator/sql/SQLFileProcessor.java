package nil.ed.easywork.generator.sql;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.comment.parser.CommentDescriptionParser;
import nil.ed.easywork.generator.sql.obj.ColumnDetails;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.type.ColTypeTransformer;
import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.sql.obj.BaseSchemaObj;
import nil.ed.easywork.sql.obj.CreateTableSchemaObj;
import nil.ed.easywork.sql.parser.AliDruidSQLParserImpl;
import nil.ed.easywork.sql.parser.ISQLParser;
import nil.ed.easywork.util.Utils;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@Slf4j
public class SQLFileProcessor implements ISQLProcessor{

    private final String sqlPath;

    private final ISQLParser sqlParser;

    private final CommentDescriptionParser commentDescriptionParser;

    private final ITypeMapper mapper;

    private final ColTypeTransformer typeTransformer;

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
        this(sqlPath, new AliDruidSQLParserImpl(), new CommentDescriptionParser(), mapper, typeTransformer);
    }

    @Override
    public List<TableDetails> process() {
        List<String> ddls = readSQLAndSplit();
        if (CollectionUtils.isEmpty(ddls)) {
            return Collections.emptyList();
        }
        List<TableDetails> tablesObjs = new LinkedList<>();
        ddls.forEach(ddl -> {
            BaseSchemaObj obj = sqlParser.parse(ddl);
            if (obj instanceof CreateTableSchemaObj) {
                List<ColumnDetails> columnDetails = new LinkedList<>();
                CreateTableSchemaObj tableObj = (CreateTableSchemaObj) obj;
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
                            .forEach(description -> field.getDescriptionMap().computeIfAbsent(description.getFunc(),
                                    k -> new LinkedList<>()).add(description));
                    columnField.setComment(getRealComment(columnField.getComment()));
                    columnDetails.add(field);
                });
                String name = getCommentDescription(tableObj.getComment());
                if (StringUtils.isBlank(name)) {
                    name = tableObj.getName().replaceAll("_", " ");
                }
                TableDetails tableDetails = new TableDetails(name, tableObj, columnDetails, getRealComment(tableObj.getComment()));
                tablesObjs.add(tableDetails);
            }
        });
        return tablesObjs;
    }

    private static final Set<Character> PUNC_CHAR_SET = Sets.newHashSet('，', '。', ',', '.');
    private String getRealComment(String comment) {
        String tmpComment = Optional.ofNullable(comment)
                .map(c -> c.replaceAll("\\{\\{[\\s\\S]*?}}".toString(), ""))
                .orElse(StringUtils.EMPTY);
        if (StringUtils.isBlank(tmpComment)) {
            return StringUtils.EMPTY;
        }
        tmpComment = tmpComment.trim();
        int index = tmpComment.length() - 1;
        while (PUNC_CHAR_SET.contains(tmpComment.charAt(index))) {
            index--;
        }
        return tmpComment.substring(0, index + 1);
    }

    private String transformType(String type) {
       if (typeTransformer != null) {
           return typeTransformer.transform(type);
       }
       return type;
    }

    private static final String PATTERN = "[\\s\\S]*?\\{\\{([\\s\\S]*?)}}";
    private static final Pattern REGEX_PATTERN = Pattern.compile(PATTERN);
    private String getCommentDescription(String comment) {
        if (StringUtils.isBlank(comment)) {
            return null;
        }

        Matcher matcher = REGEX_PATTERN.matcher(comment);
        if (matcher.find()) {
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
