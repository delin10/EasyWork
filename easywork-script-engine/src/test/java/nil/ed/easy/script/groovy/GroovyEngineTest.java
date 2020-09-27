package nil.ed.easy.script.groovy;

import junit.framework.TestCase;
import nil.ed.easywork.util.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GroovyEngineTest extends TestCase {

    public void testExecuteConsumeMethod() throws IOException {
        GroovyEngine engine = new GroovyEngine();
        String script =
                Utils.readText(GroovyEngineTest.class.getResource("/Test.groovy").getFile(), StandardCharsets.UTF_8);
        engine.executeConsumeMethod(script, "run", "1", "2");

    }
}