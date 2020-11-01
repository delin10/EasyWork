package nil.ed.easywork.generator.generator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author lidelin.
 */
public class JsonToVoGenerator {

    private Collection<AnnotationSpec> jacksonAnnos = new LinkedList<>();
    private Collection<AnnotationSpec> swaggerAnnos = new LinkedList<>();;
    private Collection<AnnotationSpec> lombokAnnos  = new LinkedList<>();;

    {
        JavaType[] jacksonAnnos = {
            JavaType.create("com.fasterxml.jackson.annotation.JsonInclude"),
            JavaType.create("com.fasterxml.jackson.databind.PropertyNamingStrategy"),
            JavaType.create("com.fasterxml.jackson.databind.annotation.JsonNaming"),
        };

        JavaType[] swaggerAnnos = {
            JavaType.create("io.swagger.annotations.ApiModel"),
            JavaType.create("io.swagger.annotations.ApiModelProperty")
        };

        JavaType[] lombokAnnos = {
            JavaType.create("lombok.Data"),
            JavaType.create("lombok.experimental.Accessors")
        };

        transfer(jacksonAnnos, this.jacksonAnnos);
        transfer(swaggerAnnos, this.swaggerAnnos);
        transfer(lombokAnnos, this.lombokAnnos);

        this.jacksonAnnos.forEach(anno -> {

        });
    }

    public void transfer(JavaType[] types, Collection<AnnotationSpec> container) {
        Arrays.stream(types)
                .map(anno -> {
                    AnnotationSpec.Builder builder = AnnotationSpec.builder(ClassName.get(anno.getPkg(), anno.getSimpleTypeName()));
                    if (anno.getFullyName().contains("PropertyNamingStrategy")) {
                        builder.addMember("value", "PropertyNamingStrategy.SnakeCaseStrategy.class");
                    } else if (anno.getFullyName().contains("JsonInclude")) {
                        builder.addMember("value", "JsonInclude.Include.NON_NULL");
                    }
                    return builder.build();
                })
                .forEach(container::add);
    }

    public String generate(String basePackage, String entity, String json) throws IOException {
        JSONObject jo = JSON.parseObject(json);
        JSONPath path = JSONPath.compile("$.data.list[0]");
        Object object = path.eval(jo);
        boolean isList = true;
        if (object == null) {
            isList = false;
            object = JSONPath.compile("$.data");
        }
        String voClassName = entity + (isList ? "ListItemVO" : "VO");
        assert object instanceof JSONObject;
        JSONObject jObject = (JSONObject) object;
        MethodSpec parse = MethodSpec.methodBuilder("parse")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(basePackage, voClassName))
                .addParameter(ClassName.get(basePackage + ".entity", entity + "Entity"), "entity")
                .addStatement(voClassName + " vo = new " + voClassName + "()")
                .addStatement("BeanUtils.copyProperties(entity, vo);")
                .addStatement("return vo;")
                .build();

        TypeSpec.Builder voBuilder = TypeSpec.classBuilder(entity + "ListItemVO")
                .addModifiers(Modifier.PUBLIC);
        jObject.forEach((k, v) -> {
            voBuilder.addField(String.class, NamingTranslatorSingleton.UNDERLINE_TO_CAMEL.trans(k), Modifier.PRIVATE);
        });
        jacksonAnnos.forEach(voBuilder::addAnnotation);
        lombokAnnos.forEach(voBuilder::addAnnotation);
        TypeSpec vo = voBuilder.addMethod(parse)
                .build();
        JavaFile javaFile = JavaFile.builder(basePackage, vo)
                .build();

        javaFile.writeTo(System.out);
        return "";
    }

    public Map<String, JavaType> resolveType(JSONObject jsonObject) {
        jsonObject.forEach((k, v) -> {

        });
        return null;
    }

    public static void main(String[] args) throws IOException {
        JsonToVoGenerator generator = new JsonToVoGenerator();
        generator.generate("com.kyle", "List", "{\n" +
                "\t\"code\": 200,\n" +
                "\t\"message\": \"ok\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"total_count\": 200,\n" +
                "\t\t\"list\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"operation_time\": \"2020-03-04 12:32:12\",\n" +
                "\t\t\t\t\"id\": 212,\n" +
                "\t\t\t\t\"op_type\": \"创建\",\n" +
                "\t\t\t\t\"content\": \"创建: 测试一组\",\n" +
                "\t\t\t\t\"operator\": \"lidelin\",\n" +
                "\t\t\t\t\"ip\": \"127.0.0.1\"\n" +
                "\t\t\t}\n" +
                "\t\t\t\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}");
    }

}
