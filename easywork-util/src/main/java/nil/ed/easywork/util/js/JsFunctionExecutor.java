package nil.ed.easywork.util.js;

import org.apache.commons.io.IOUtils;

import javax.script.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public class JsFunctionExecutor {

    private ScriptEngine scriptEngine;

    public JsFunctionExecutor() {
        ScriptEngineManager sem = new ScriptEngineManager();
        this.scriptEngine = sem.getEngineByName("js");
    }

    public void defineScript(String script) throws ScriptException {
        scriptEngine.eval(script);
    }

    public void loadScript(String jsFile) throws IOException, ScriptException {
        List<String> lines = IOUtils.readLines(new FileInputStream(new File(jsFile)), StandardCharsets.UTF_8);
        String jsScript = String.join("\n", lines);
        defineScript(jsScript);
    }

    public Object invoke(String name, Object...args) throws ScriptException, NoSuchMethodException {
        Invocable inv = (Invocable) scriptEngine;
        return inv.invokeFunction(name, args);
    }

}
