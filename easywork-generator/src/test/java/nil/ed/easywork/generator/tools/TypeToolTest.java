package nil.ed.easywork.generator.tools;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypeToolTest {

    @Test
    public void getGenericType() {
        TypeTool tool = new TypeTool();
        Assert.assertEquals("String", tool.getGenericType("List<String>"));
        Assert.assertEquals("String", tool.getGenericType("String"));
    }
}