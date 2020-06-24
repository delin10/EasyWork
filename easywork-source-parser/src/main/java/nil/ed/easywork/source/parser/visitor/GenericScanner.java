package nil.ed.easywork.source.parser.visitor;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.struct.BaseClass;
import nil.ed.easywork.source.parser.ProcessorSingleton;

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

    public GenericScanner() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public BaseClass visitClass(ClassTree classTree, Void nil) {
        BaseClass clazz =  (BaseClass) ProcessorSingleton.CLASS_INFO_PROCESSOR.apply(classTree);
        if (unit != null) {
            clazz.setPkg(unit.getPackageName().toString());
            List<String> imports = (List<String>) unit.getImports().parallelStream()
                    .map(im -> im.getQualifiedIdentifier().toString())
                    .collect(Collectors.toList());
            clazz.getImports().addAll(imports);
        }

        return clazz;
    }

}

