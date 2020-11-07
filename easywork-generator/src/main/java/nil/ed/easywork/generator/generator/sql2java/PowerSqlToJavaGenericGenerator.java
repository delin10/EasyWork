package nil.ed.easywork.generator.generator.sql2java;

import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.ContextMediator;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.java.obj.entity.ModelField;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.tools.TypeTool;
import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.source.obj.type.BaseClass;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.sql.obj.ColumnField;
import nil.ed.easywork.template.ITemplateEngineAdapter;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author lidelin.
 */
@Slf4j
public class PowerSqlToJavaGenericGenerator {

    @SuppressWarnings("rawtypes")
    private ITemplateEngineAdapter templateEngineAdapter;

    private ContextMediator mediator = new ContextMediator(null, null);

    private SQLFileProcessor processor;

    private ITypeMapper mapper = new AdsTypeMapper();

    private Config config;

    @SuppressWarnings("rawtypes")
    public PowerSqlToJavaGenericGenerator(Config config, ITemplateEngineAdapter adapter, SQLFileProcessor processor) {
        this.config = config;
        this.templateEngineAdapter = adapter;
        this.processor = processor;
    }

    public void generate(PowerTemplateContext context) {
        List<TableDetails> tables = processor.process();
        tables.forEach(details -> {
            GenerateContextBuilder builder = new GenerateContextBuilder();
            buildContext(details, builder);
            builder.set(GenerateContextBuilder.ROOT, config);
            builder.registerTool(new TypeTool());
            Map<String, Object> cxt = builder.build();
            context.getTemplateConfigCache()
                    .forEach((k, config) -> {
                        try {
                            builder.set(GenerateContextBuilder.CURRENT_IMPORTS, new LinkedList<>());
                            config.doAction(cxt, config.getTemplateText(), this.config);
                            cxt.remove(GenerateContextBuilder.CURRENT_IMPORTS);
                        } catch (Exception e) {
                            log.error("Failed to render template {}, config {}", k, config, e);
                            System.exit(1);
                        }
                    });
        });
    }

    private String sqlTypeToJavaType(String colType, String descType) {
        return mapper.map(colType);
    }

    private BaseClass buildContext(TableDetails details, GenerateContextBuilder builder) {
        String className = NamingTranslatorSingleton.UNDERLINE_TO_PASCAL.trans(processPrefix(details.getTableObj().getName()));
        BaseClass clazz = new BaseClass();
        clazz.setName(className);
        Map<FunctionEnum, List<String>> fieldMap = new HashMap<>(FunctionEnum.values().length);
        Map<String, ColumnField> fieldColMap = new HashMap<>(details.getColumnDetails().size());
        Map<String, ModelField> colFieldMap = new HashMap<>(details.getColumnDetails().size());
        List<CommentDescription> fieldDesc = new LinkedList<>();
        Set<String> importSet = new HashSet<>();
        for (FunctionEnum f : FunctionEnum.values()) {
            fieldMap.put(f, new LinkedList<>());
        }
        details.getColumnDetails()
                .forEach(columnDetails -> {
                    String name = NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(columnDetails.getField().getName());
                    JavaType type = new JavaType(sqlTypeToJavaType(columnDetails.getField().getType(),null));
                    ModelField field = new ModelField(name, type);
                    field.setPrimary(columnDetails.getField().isPrimary());
                    clazz.getFields().add(field);
                    if (config.needImport(type.getFullyName())) {
                        importSet.add(config.getType(type.getName()));
                        Queue<JavaType> queue = new LinkedList<>();
                        if (CollectionUtils.isNotEmpty(type.getGeneric())) {
                            queue.addAll(type.getGeneric());
                        }
                        JavaType cur;
                        while ((cur = queue.poll()) != null) {
                            importSet.add(config.getType(cur.getName()));
                            if (CollectionUtils.isNotEmpty(cur.getGeneric())) {
                                queue.addAll(cur.getGeneric());
                            }
                        }
                    }
                    columnDetails.getDescriptionMap().forEach((func, desc) -> {
                        List<String> fields = fieldMap.get(func);
                        fields.add(field.getName());
                        fieldDesc.add(desc);
                    });
                    fieldColMap.put(name, columnDetails.getField());
                    colFieldMap.put(columnDetails.getField().getName(), field);
                });
        importSet.stream().map(ImportItem::new).forEach(clazz.getImports()::add);
        builder.set(GenerateContextBuilder.ENTITY, clazz);
        builder.set(GenerateContextBuilder.TABLE, details);
        builder.set(GenerateContextBuilder.FIELD_COL_MAP, fieldColMap);
        builder.set(GenerateContextBuilder.COL_FIELD_MAP, colFieldMap);
        builder.set(GenerateContextBuilder.FIELD_DESC, fieldDesc);
        Arrays.stream(FunctionEnum.values()).forEach(f -> {
            builder.set(f.getName() + GenerateContextBuilder.FUNC_FIELDS_SUFFIX, fieldMap.get(f));
        });
        return clazz;
    }

    private String processPrefix(String name) {
        String prefix = config.getPrefix();
        if (prefix != null) {
            if (name.startsWith(prefix)) {
                return name.substring(prefix.length());
            }
        }
        return name;
    }

}
