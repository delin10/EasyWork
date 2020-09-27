package nil.ed.easy.script;

/**
 * @author lidelin.
 */
public interface ScriptEngine {

    /**
     * 执行实现了Runnable接口的方法.
     * @param script 脚本.
     * @param method 方法名.
     * @param args 方法参数.
     */
    void executeConsumeMethod(String script, String method, Object...args);

}
