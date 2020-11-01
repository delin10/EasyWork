package nil.ed.easywork.generator.generator;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import nil.ed.easywork.source.obj.Line;
import nil.ed.easywork.source.obj.access.AccessEnum;
import nil.ed.easywork.source.obj.access.BaseAccessControl;
import nil.ed.easywork.source.obj.render.MethodRender;
import nil.ed.easywork.source.obj.render.StatementSupplier;
import nil.ed.easywork.source.obj.struct.*;
import nil.ed.easywork.source.obj.type.BaseClass;
import nil.ed.easywork.source.obj.type.JavaType;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/15
 **/
public class VoEntityTransFunctionGenerator {

    private static final String PARSE_NAME = "parse";
    private static final String TO_ENTITY_NAME = "toEntity";

    public TransFunction generate(BaseClass vo, BaseClass entity) {
        Set<String> voFieldNameSet = vo.getFields()
                .stream()
                .map(BaseField::getName)
                .collect(Collectors.toSet());
        Set<String> entityFieldNameSet = entity.getFields()
                .stream()
                .map(BaseField::getName)
                .collect(Collectors.toSet());
        Set<String> intersectSet = Sets.intersection(voFieldNameSet, entityFieldNameSet).immutableCopy();
        TransFunction function = new TransFunction();
        Set<Param> parseParams = Sets.newHashSet(new Param("entity", new JavaType(entity.getFullyName())));
//        function.parse = new BaseMethod(PARSE_NAME,
//                new BaseAccessControl(AccessEnum.PUBLIC, false, true, false, false),
//                new JavaType(entity.getFullyName()), parseParams);
        StatementSupplier parseSupplier = (method, level) -> {
            List<Line> lines = new LinkedList<>();
            Line newLine = MethodRender.getNewStatement(vo.getName(), "vo");
            newLine.setIndent(level + 1);
            lines.add(newLine);
            intersectSet.forEach(f -> {
                String pascalName = NamingTranslatorSingleton.CAMEL_TO_PASCAL.trans(f);
                lines.add(new Line("vo.set" + pascalName + "(" + "entity.get" + pascalName + "())", level + 1));
            });
            lines.add(new Line("return vo;", level + 1));
            return lines;
        };
//        function.toEntity = new BaseMethod(TO_ENTITY_NAME, new MethodAccessControl(), new JavaType(entity.getFullyName()));
        MethodRender parseRender = new MethodRender(parseSupplier);
        function.parse.setRender(parseRender);
        StatementSupplier toEntityBodySupplier = (method, level) -> {
            List<Line> lines = new LinkedList<>();
            Line newLine = MethodRender.getNewStatement(entity.getName(), "entity");
            newLine.setIndent(level + 1);
            lines.add(newLine);
            intersectSet.forEach(f -> {
                String pascalName = NamingTranslatorSingleton.CAMEL_TO_PASCAL.trans(f);
                lines.add(new Line("entity.set" + pascalName + "(" + "this." + f + ")", level + 1));
            });
            lines.add(new Line("return entity;", level + 1));
            return lines;
        };
        MethodRender toEntityRender = new MethodRender(toEntityBodySupplier);
        function.toEntity.setRender(toEntityRender);
        return function;
    }

    public static class TransFunction {
        @Getter
        @Setter
        private BaseMethod parse;
        @Getter
        @Setter
        private BaseMethod toEntity;
    }

}
