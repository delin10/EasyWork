package nil.ed.easywork.generator.generator.sql2java.listener.macro;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.GenerateContextBuilder;
import nil.ed.easywork.generator.generator.sql2java.listener.TemplateRenderListener;
import nil.ed.easywork.generator.util.sorter.CheckStyleImportSortUtils;
import nil.ed.easywork.source.obj.type.ImportItem;
import org.apache.commons.collections4.CollectionUtils;

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
public class MacroTemplateRenderListener implements TemplateRenderListener {

    @Override
    public void beforeRender(Map<String, Object> context, String template, Config config) {
    }

    @Override
    @SuppressWarnings("unchecked")
    public String afterRender(Map<String, Object> context, String template, Config config, String renderResult) {
        List<ImportItem> additionalImport = new LinkedList<>();
        config.PATTERN_MAP.forEach((k, v) -> {
            Matcher matcher = k.matcher(renderResult);
            if (v.isNeedImport() && matcher.find()) {
                additionalImport.add(new ImportItem(v.getClazz()));
            }
        });

        Map<String, String> additionalPatternImport
                = (Map<String, String>) context.get(GenerateContextBuilder.ADDITIONAL_PATTERN_IMPORT);
        additionalPatternImport.forEach((k, v) -> {
            Pattern ptn = Pattern.compile("\\b" + k + "\\b");
            Matcher matcher = ptn.matcher(renderResult);
            if (matcher.find()) {
                additionalImport.add(new ImportItem(v));
            }
        });
        List<ImportItem> imports = (List<ImportItem>) context.get(GenerateContextBuilder.CURRENT_IMPORTS);
        imports = Stream.concat(imports.stream(), additionalImport.stream()).distinct().collect(Collectors.toList());
        Map<String, List<ImportItem>> map = CheckStyleImportSortUtils.sortAndClassify(imports);
        List<ImportItem> thirdPartyImports = map.get(CheckStyleImportSortUtils.THIRD_PARTY);
        List<ImportItem> javaImports = map.get(CheckStyleImportSortUtils.JAVA_PARTY);
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(thirdPartyImports)) {
            for (ImportItem i : thirdPartyImports) {
                appendOneImport(builder, i);
            }
            if (CollectionUtils.isNotEmpty(javaImports)) {
                builder.append(System.lineSeparator());
            }
        }
        if (CollectionUtils.isNotEmpty(javaImports)) {
            for (ImportItem i : javaImports) {
                appendOneImport(builder, i);
            }
        }
        return renderResult.replaceAll("__IMPORTS__", builder.toString());
    }

    private void appendOneImport(StringBuilder builder, ImportItem imp) {
        builder.append("import ");
        if (imp.isStatic()) {
            builder.append("static ");
        }
        builder.append(imp.getContent());
        builder.append(";");
        builder.append(System.lineSeparator());
    }
}
