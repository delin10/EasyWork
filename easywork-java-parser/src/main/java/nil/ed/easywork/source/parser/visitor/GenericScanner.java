package nil.ed.easywork.source.parser.visitor;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.type.BaseClass;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.source.parser.processor.ProcessorSingleton;
import nil.ed.easywork.util.FlowUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public class GenericScanner extends TreeScanner<BaseClass, Void> {

    private JCTree.JCCompilationUnit unit;

    public GenericScanner(JCTree.JCCompilationUnit unit) {
        this.unit = unit;
    }

    public GenericScanner() { }

    @Override
    public BaseClass visitClass(ClassTree classTree, Void nil) {
        BaseClass clazz =  (BaseClass) ProcessorSingleton.CLASS_INFO_PROCESSOR.apply(classTree);
        if (unit != null) {
            clazz.setPkg(unit.getPackageName().toString());
            List<ImportItem> imports = FlowUtils.continueStreamIfNotNull(unit.getImports())
                    .map(im -> new ImportItem(im.getQualifiedIdentifier().toString())
                            .setStatic(im.isStatic()))
                    .collect(Collectors.toList());
            clazz.getImports().addAll(imports);
        }

        return clazz;
    }

}

