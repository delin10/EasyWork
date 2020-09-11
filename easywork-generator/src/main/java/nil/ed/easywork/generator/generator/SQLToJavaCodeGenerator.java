package nil.ed.easywork.generator.generator;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.comment.obj.CommentDescription;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.ContextMediator;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.generator.java.obj.entity.ModelField;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.type.ITypeMapper;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.util.sorter.CheckStyleImportSortUtils;
import nil.ed.easywork.source.obj.struct.BaseClass;
import nil.ed.easywork.source.obj.struct.JavaType;
import nil.ed.easywork.sql.obj.ColumnField;
import nil.ed.easywork.template.ITemplateEngineAdapter;
import nil.ed.easywork.util.Utils;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@Setter
@Getter
public class SQLToJavaCodeGenerator {

    private ITemplateEngineAdapter templateEngineAdapter;

    private ContextMediator mediator = new ContextMediator(null, null);

    private SQLFileProcessor processor;

    private ITypeMapper mapper = new AdsTypeMapper();

    private Config config;

    public SQLToJavaCodeGenerator(Config config, ITemplateEngineAdapter adapter, SQLFileProcessor processor) {
        this.config = config;
        this.templateEngineAdapter = adapter;
        this.processor = processor;
    }

    @SuppressWarnings("unchecked")
    public void generate(TemplateContext context) {
        List<TableDetails> tables = processor.process();
        tables.forEach(details -> {
            GenerateContextBuilder builder = new GenerateContextBuilder();
            BaseClass clazz = buildContext(details, builder);
            builder.set(GenerateContextBuilder.ROOT, config);
            Map<String, Object> cxt = builder.build();
            context.getTemplateCache()
                    .forEach((k, triple) -> {
                String afterRender = templateEngineAdapter.process(triple.getRight(), cxt);
                Utils.writeToFile(config.getBasePath() + "/" + triple.left,
                        String.format(triple.middle, clazz.getName()), afterRender);
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
        Map<String, CommentDescription> fieldDesc = new HashMap<>(details.getColumnDetails().size());
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
                    if (!"java.lang".equals(type.getPkg())) {
                        clazz.getImports().add(type.getFullyName());
                    }
                    columnDetails.getDescriptionMap().forEach((func, desc) -> {
                        List<String> fields = fieldMap.get(func);
                        fields.add(field.getName());
                        fieldDesc.put(field.getName() + "-" + func.getName(), desc);
                    });
                    fieldColMap.put(name, columnDetails.getField());
                    colFieldMap.put(columnDetails.getField().getName(), field);
                });
        CheckStyleImportSortUtils.sort(clazz.getImports());
        builder.set(GenerateContextBuilder.ENTITY, clazz);
        builder.set(GenerateContextBuilder.TABLE, details.getTableObj());
        builder.set(GenerateContextBuilder.FIELD_COL_MAP, fieldColMap);
        builder.set(GenerateContextBuilder.COL_FIELD_MAP, colFieldMap);
        builder.set(GenerateContextBuilder.FIELD_DESC, fieldDesc);
        builder.set(GenerateContextBuilder.INSERT_FIELDS, fieldMap.get(FunctionEnum.INSERT));
        builder.set(GenerateContextBuilder.LIST_FIELDS, fieldMap.get(FunctionEnum.LIST));
        builder.set(GenerateContextBuilder.UPDATE_FIELDS, fieldMap.get(FunctionEnum.UPDATE));
        builder.set(GenerateContextBuilder.SEARCH_FIELDS, fieldMap.get(FunctionEnum.SEARCH));
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
