package nil.ed.easywork.generator.context;

import nil.ed.easywork.util.ClasspathFileUtils;
import org.junit.Assert;
import org.junit.Test;

public class PowerTemplateContextTest {

    @Test
    public void testLoad() {
        PowerTemplateContext context = new PowerTemplateContext(ClasspathFileUtils.getClassPath("/new_tpl/test"));
        Assert.assertEquals(1, context.getTemplateConfigCache().size());
        Assert.assertTrue(context.getTemplateConfigCache().containsKey("SAMPLE"));
        Assert.assertEquals("// tpl define", context.getTemplateConfig("SAMPLE").getTemplateText());
    }

}