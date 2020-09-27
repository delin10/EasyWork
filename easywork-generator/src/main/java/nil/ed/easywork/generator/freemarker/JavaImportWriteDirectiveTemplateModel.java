package nil.ed.easywork.generator.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.util.sorter.CheckStyleImportSortUtils;
import nil.ed.easywork.template.support.FreeMarkerSupport;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class JavaImportWriteDirectiveTemplateModel implements TemplateDirectiveModel {

    private ThreadLocal<List<String>> imports = ThreadLocal.withInitial(LinkedList::new);

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            List<String> imports = FreeMarkerSupport.getList(GenerateContextBuilder.CURRENT_IMPORTS, env);;
            imports = imports.stream().distinct().collect(Collectors.toList());
            Map<String, List<String>> map = CheckStyleImportSortUtils.sortAndClassify(imports);
            List<String> thirdPartyImports = map.get(CheckStyleImportSortUtils.THIRD_PARTY);
            List<String> javaImports = map.get(CheckStyleImportSortUtils.JAVA_PARTY);
            if (CollectionUtils.isNotEmpty(thirdPartyImports)) {
                for (String i : thirdPartyImports) {
                    writeOneImport(env, i);
                }
                env.getOut().write(System.lineSeparator());
            }
            if (CollectionUtils.isNotEmpty(javaImports)) {
                for (String i : javaImports) {
                    writeOneImport(env, i);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeOneImport(Environment env, String imp) {
        try {
            env.getOut().write("import ");
            env.getOut().write(imp);
            env.getOut().write(";");
            env.getOut().write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String imports = "import com.kuaikan.ads.kyle.common.exception.ErrorType;\n" +
                "import lombok.Getter;";
        String[] importItems = imports.split("\n");
        for (String item : importItems) {
            String pkg = item.substring("import".length(), item.length() - 1).trim();
            System.out.print("<@JavaImportIn value=\"");
            System.out.print(pkg);
            System.out.println("\"/>");
        }
        System.out.println("<@JavaImportOut/>");
    }
}
