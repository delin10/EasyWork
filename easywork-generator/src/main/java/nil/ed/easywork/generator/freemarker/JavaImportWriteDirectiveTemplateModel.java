package nil.ed.easywork.generator.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.util.sorter.CheckStyleImportSortUtils;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.template.support.FreeMarkerSupport;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class JavaImportWriteDirectiveTemplateModel implements TemplateDirectiveModel {

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        try {
            String tmpStr = env.getMainTemplate().toString();
            Object cxt = FreeMarkerSupport.getRawContext(env);
            Config config = (Config) Ognl.getValue(GenerateContextBuilder.ROOT, cxt);
            List<ImportItem> additionalImport = new LinkedList<>();
            config.PATTERN_MAP.forEach((k, v) -> {
                Matcher matcher = k.matcher(tmpStr);
                if (v.isNeedImport() && matcher.find()) {
                    additionalImport.add(new ImportItem(v.getClazz()));
                }
            });

            Map<String, String> additionalPatternImport
                    = (Map<String, String>) Ognl.getValue(GenerateContextBuilder.ADDITIONAL_PATTERN_IMPORT, cxt);
            additionalPatternImport.forEach((k, v) -> {
                Pattern ptn = Pattern.compile("\\b" + k + "\\b");
                Matcher matcher = ptn.matcher(tmpStr);
                if (matcher.find()) {
                    additionalImport.add(new ImportItem(v));
                }
            });
            List<ImportItem> imports = FreeMarkerSupport.getList(GenerateContextBuilder.CURRENT_IMPORTS, env);
            imports = Stream.concat(imports.stream(), additionalImport.stream()).distinct().collect(Collectors.toList());
            Map<String, List<ImportItem>> map = CheckStyleImportSortUtils.sortAndClassify(imports);
            List<ImportItem> thirdPartyImports = map.get(CheckStyleImportSortUtils.THIRD_PARTY);
            List<ImportItem> javaImports = map.get(CheckStyleImportSortUtils.JAVA_PARTY);
            if (CollectionUtils.isNotEmpty(thirdPartyImports)) {
                for (ImportItem i : thirdPartyImports) {
                    writeOneImport(env, i);
                }
                env.getOut().write(System.lineSeparator());
            }
            if (CollectionUtils.isNotEmpty(javaImports)) {
                for (ImportItem i : javaImports) {
                    writeOneImport(env, i);
                }
            }
        } catch (IOException | OgnlException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeOneImport(Environment env, ImportItem imp) {
        try {
            env.getOut().write("import ");
            if (imp.isStatic()) {
                env.getOut().write("static ");
            }
            env.getOut().write(imp.getContent());
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
