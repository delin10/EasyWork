package nil.ed.easywork.generator;

import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.ContextMediator;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.context.TemplateContext;
import nil.ed.easywork.generator.java.obj.entity.EntityField;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.type.TypeMapper;
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

    private String entityTemplateId = TemplateContext.ENTITY;
    private String mapperTemplateId = TemplateContext.MAPPER;
    private String serviceTemplateId = TemplateContext.SERVICE;
    private String controllerTemplateId = TemplateContext.CONTROLLER;
    private String mapperXmlTemplateId = TemplateContext.MAPPER_XML;


    private SQLFileProcessor processor;

    private TypeMapper mapper = new TypeMapper();

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
            String entity = templateEngineAdapter.process(context.getTemplate(entityTemplateId), cxt);
            String mapper = templateEngineAdapter.process(context.getTemplate(mapperTemplateId), cxt);
            String mapperXml = templateEngineAdapter.process(context.getTemplate(mapperXmlTemplateId), cxt);
            String service = templateEngineAdapter.process(context.getTemplate(serviceTemplateId), cxt);
            String controller = templateEngineAdapter.process(context.getTemplate(controllerTemplateId), cxt);
//            Utils.writeToFile(config.getBasePath(), "entity/" + clazz.getName() + ".java", entity);
//            Utils.writeToFile(config.getBasePath(), "mapper/" + clazz.getName() + "Mapper.java", mapper);
            Utils.writeToFile(config.getBasePath(), "mapper/" + clazz.getName() + "Mapper.xml", mapperXml);
//            Utils.writeToFile(config.getBasePath(), "service/" + clazz.getName() + "Service.java", service);
//            Utils.writeToFile(config.getBasePath(), "controller/" + clazz.getName() + "Controller.java", controller);
        });
    }

    private String sqlTypeToJavaType(String colType, String descType) {
        return mapper.map(colType.toUpperCase());
    }

    private BaseClass buildContext(TableDetails details, GenerateContextBuilder builder) {
        String className = NamingTranslatorSingleton.UNDERLINE_TO_PASCAL.trans(processPrefix(details.getTableObj().getName()));
        BaseClass clazz = new BaseClass();
        clazz.setName(className);
        Map<FunctionEnum, List<EntityField>> fieldMap = new HashMap<>(FunctionEnum.values().length);
        Map<String, ColumnField> fieldColMap = new HashMap<>(details.getColumnDetails().size());
        Map<String, EntityField> colFieldMap = new HashMap<>(details.getColumnDetails().size());
        for (FunctionEnum f : FunctionEnum.values()) {
            fieldMap.put(f, new LinkedList<>());
        }
        details.getColumnDetails()
                .forEach(columnDetails -> {
                    String name = NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(columnDetails.getField().getName());
                    JavaType type = new JavaType(sqlTypeToJavaType(columnDetails.getField().getType(),null));
                    EntityField field = new EntityField(name, type);
                    field.setPrimary(columnDetails.getField().isPrimary());
                    clazz.getFields().add(field);
                    if (!"java.lang".equals(type.getPkg())) {
                        clazz.getImports().add(type.getFullyName());
                    }
                    columnDetails.getDescriptionMap().forEach((func, desc) -> {
                        List<EntityField> fields = fieldMap.get(func);
                        fields.add(field);
                    });
                    fieldColMap.put(name, columnDetails.getField());
                    colFieldMap.put(columnDetails.getField().getName(), field);
                });
        builder.set(GenerateContextBuilder.ENTITY, clazz);
        builder.set(GenerateContextBuilder.TABLE, details.getTableObj());
        builder.set(GenerateContextBuilder.FIELD_COL_MAP, fieldColMap);
        builder.set(GenerateContextBuilder.COL_FIELD_MAP, colFieldMap);
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
