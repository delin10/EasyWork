package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.tools.javac.tree.JCTree;
import nil.ed.easywork.source.obj.access.support.AccessControlSupport;
import nil.ed.easywork.source.obj.struct.BaseField;
import nil.ed.easywork.source.obj.struct.BaseMethod;
import nil.ed.easywork.source.obj.type.BaseClass;
import nil.ed.easywork.source.obj.type.ImportItem;
import nil.ed.easywork.source.parser.support.ParserSupport;
import nil.ed.easywork.util.FlowUtils;

import java.util.List;
import java.util.function.Function;

/**
 * @author delin10
 * @since 2020/5/28
 **/
class ClassTreeParseConsumer implements Function<ClassTree, BaseClass> {

    @Override
    public BaseClass apply(ClassTree tree) {
        JCTree.JCClassDecl clazzDef = (JCTree.JCClassDecl) tree;
        BaseClass clazz =  new BaseClass(null, tree.getSimpleName().toString());
        clazz.setAccess(AccessControlSupport.getBaseAccessControl(tree));
        List<? extends AnnotationTree> annotation = tree.getModifiers().getAnnotations();
        List<ImportItem> old = ParserSupport.IMPORT_CTX.get();
        try {
            ParserSupport.IMPORT_CTX.set(clazz.getImports());
            FlowUtils.continueStreamIfNotNull(annotation)
                    .map(ParserSupport::getJavaAnnotation)
                    .forEach(clazz.getAnnotations()::add);
            FlowUtils.iterateIfNotNull(tree.getMembers()).forEach(mem -> {
                Object result = ProcessorSingleton.FIELD_TREE_PROCESSOR.apply(mem);
                if (result != null && result != ProcessorSingleton.NON_SUPPORT_OBJ) {
                    clazz.getFields().add((BaseField) result);
                }
            });

            FlowUtils.iterateIfNotNull(clazzDef.defs)
                    .forEach(def -> {
                        FlowUtils.continueIfValidCast(def, JCTree.JCMethodDecl.class)
                                .map(ProcessorSingleton.METHOD_TREE_PROCESSOR::apply)
                                .flatMap(m -> FlowUtils.continueIfValidCast(m, BaseMethod.class))
                                .ifPresent(clazz.getMethods()::add);
                    });
        } finally {
            ParserSupport.IMPORT_CTX.set(old);
        }
        return clazz;
    }

}
