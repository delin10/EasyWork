package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnderlineToCamelCaseNamingTranslatorTest {

    private INamingTranslator translator = new UnderlineToCamelCaseNamingTranslator();

    @Test
    public void trans() {

        String name = "a_b_b_d_e_f";
        String name0 = "my_trans";
        String name1 = "test__sdsds_sds";
        String name2 = "my_title_TT_ss";

        Assert.assertEquals("aBBDEF", translator.trans(name));
        Assert.assertEquals("myTrans", translator.trans(name0));
        Assert.assertEquals("testSdsdsSds", translator.trans(name1));
        Assert.assertEquals("myTitleTTSs", translator.trans(name2));
    }
}