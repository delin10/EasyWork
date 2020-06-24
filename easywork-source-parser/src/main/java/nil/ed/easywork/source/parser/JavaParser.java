package nil.ed.easywork.source.parser;

/**
 * @author delin10
 * @since 2020/5/28
 **/
public interface JavaParser<R> {

    /**
     * 语法分析源代码
     * @param source 源代码
     * @return 分析树
     */
    R parse(CharSequence source);

}
