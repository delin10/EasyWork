package nil.ed.easywork.source.obj.type.support;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypeSupportTest {

    @Test
    public void resolveJavaType() {
        Assert.assertEquals("AbstractGenericClass<AbstractGenericClass<Object>>",
                TypeSupport.resolveJavaType("AbstractGenericClass<AbstractGenericClass<Object>>").getSimpleTypeName());
    }
}