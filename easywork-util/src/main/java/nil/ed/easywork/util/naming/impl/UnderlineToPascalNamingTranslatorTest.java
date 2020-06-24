package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnderlineToPascalNamingTranslatorTest {

    private INamingTranslator translator = new UnderlineToPascalNamingTranslator();

    @Test
    public void transUpper() {
        String name = "a_b_b_d_e_f";
        String name0 = "my_trans";
        String name1 = "test__sdsds_sds";
        String name2 = "my_title_TT_ss";

        Assert.assertEquals("ABBDEF", translator.trans(name));
        Assert.assertEquals("MyTrans", translator.trans(name0));
        Assert.assertEquals("TestSdsdsSds", translator.trans(name1));
        Assert.assertEquals("MyTitleTTSs", translator.trans(name2));
    }
}