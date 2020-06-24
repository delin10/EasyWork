package nil.ed.easywork.source.parser.processor;

import com.sun.source.tree.ClassTree;
import nil.ed.easywork.source.obj.struct.BaseClass;

import java.util.function.Function;

/**
 * @author delin10
 * @since 2020/5/28
 **/
class ClassTreeParseConsumer implements Function<ClassTree, BaseClass> {

    @Override
    public BaseClass apply(ClassTree classTree) {
        BaseClass clazz = new BaseClass(null, classTree.getSimpleName().toString());
        return clazz;
    }

}
