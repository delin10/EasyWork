package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;
import org.apache.commons.lang3.StringUtils;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public class PascalToCamelNamingStrategy implements INamingTranslator {

    @Override
    public String trans(String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

}
