package nil.ed.easywork.generator.context;

import nil.ed.easywork.comment.enums.FunctionEnum;
import nil.ed.easywork.source.obj.LineOutput;
import nil.ed.easywork.generator.java.obj.entity.ModelField;
import nil.ed.easywork.source.obj.struct.*;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import nil.ed.easywork.generator.sql.obj.TableDetails;
import nil.ed.easywork.generator.type.impl.TypeMapper;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public class ContextMediator {

    private SQLContext sqlContext;

    private JavaContext javaContext;

    private final TypeMapper mapper = new TypeMapper();

    public ContextMediator(SQLContext sqlContext, JavaContext javaContext) {
        this.sqlContext = sqlContext;
        this.javaContext = javaContext;
    }

    public ContextMediator(SQLContext sqlContext) {
        this(sqlContext, null);
    }

    public ContextMediator(JavaContext javaContext) {
        this(null, javaContext);
    }
    public SQLContext getSqlContext() {
        return sqlContext;
    }

    public void setSqlContext(SQLContext sqlContext) {
        this.sqlContext = sqlContext;
    }

    public JavaContext getJavaContext() {
        return javaContext;
    }

    public void setJavaContext(JavaContext javaContext) {
        this.javaContext = javaContext;
    }

    public JavaContext sqlContextToJavaContext(SQLContext sqlContext) {
        javaContext = new JavaContext();
        LineOutput output = new LineOutput();
        sqlContext.getAll()
                .stream()
                .map(this::mapToJava)
                .forEach(clazz -> javaContext.put(clazz.getName(), clazz));
        return javaContext;
    }

    private BaseClass mapToJava(TableDetails details) {
        SqlTransResult result = new SqlTransResult();
        String className = NamingTranslatorSingleton.UNDERLINE_TO_PASCAL.trans(processPrefix(details.getTableObj().getName()));
        BaseClass clazz = new BaseClass();
        clazz.setName(className);

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
                });
        return clazz;
    }

    private BaseClass buildMapper(BaseClass entity, TableDetails details) {
        BaseClass mapper = new BaseClass();
        JavaType longType = new JavaType("java.lang.Long");
        String camelName = NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(details.getTableObj().getName());
        mapper.setName(entity.getName() + "Mapper");
        BaseMethod insertMethod = new BaseMethod("insert", longType);
        Param entityParam = new Param();
        entityParam.setName(camelName);
        entityParam.setType(new JavaType(entity.getFullyName()));
        insertMethod.getParams().add(entityParam);
        mapper.getMethods().add(insertMethod);

        BaseMethod updateMethod = new BaseMethod("update", longType);
        updateMethod.getParams().add(entityParam);
        mapper.getMethods().add(updateMethod);

        return mapper;
    }

    private BaseClass buildMapperClass(TableDetails details) {
        String className = NamingTranslatorSingleton.UNDERLINE_TO_PASCAL.trans(processPrefix(details.getTableObj().getName()));
        BaseClass clazz = new BaseClass();
        clazz.setName(className);

        details.getColumnDetails().forEach(col -> {
            col.getDescriptionMap().forEach((func, desc) -> {
                if (func == FunctionEnum.INSERT) {

                }
            });
        });
        return clazz;
    }

    private BaseMethod buildInsertMethod(TableDetails details) {
        JavaType longType = new JavaType("java.lang.Long");
        BaseMethod method = new BaseMethod("insert", longType);
        return method;
    }

    private String processPrefix(String name) {
        return name;
    }

    private String sqlTypeToJavaType(String colType, String descType) {
        return mapper.map(colType.toUpperCase());
    }

}
