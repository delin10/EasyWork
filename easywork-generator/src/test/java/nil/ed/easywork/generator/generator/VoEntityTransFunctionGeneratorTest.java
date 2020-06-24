package nil.ed.easywork.generator.generator;

import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.struct.BaseClass;
import nil.ed.easywork.source.parser.JavaParser;
import nil.ed.easywork.source.parser.JavacParserImpl;
import nil.ed.easywork.source.parser.visitor.GenericScanner;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VoEntityTransFunctionGeneratorTest {

    @Test
    public void generate() throws Exception{
        JavaParser<JCTree.JCCompilationUnit> parser = new JavacParserImpl();

        String dir = "D:\\000delin\\workspace\\livechat\\livechat-common\\src\\main\\java\\nil\\ed\\livechat\\common";
        List<String> voLines = IOUtils.readLines(Files.newInputStream(Paths.get(dir, "vo/BaseRoomVO.java")), StandardCharsets.UTF_8);
        List<String> entityLines = IOUtils.readLines(Files.newInputStream(Paths.get(dir, "entity/RoomEntity.java")), StandardCharsets.UTF_8);
        String voSrc = String.join("\n", voLines);
        String entitySrc = String.join("\n", entityLines);
        JCTree.JCCompilationUnit voUnit = parser.parse(voSrc);
        JCTree.JCCompilationUnit entityUnit = parser.parse(entitySrc);
        GenericScanner voScanner = new GenericScanner(voUnit);
        GenericScanner entityScanner = new GenericScanner(entityUnit);
        BaseClass voClazz = voScanner.visitCompilationUnit(voUnit, null);
        BaseClass entityClazz = entityScanner.visitCompilationUnit(entityUnit, null);

        VoEntityTransFunctionGenerator generator = new VoEntityTransFunctionGenerator();
        VoEntityTransFunctionGenerator.TransFunction function = generator.generate(voClazz, entityClazz);
        System.out.println(function.getParse());
        System.out.println(function.getToEntity());
    }
}