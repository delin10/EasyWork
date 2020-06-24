package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;

/**
 * @author delin10
 * @since 2020/6/15
 **/
public class CamelToPascalNamingTranslator implements INamingTranslator {
    @Override
    public String trans(String name) {
        StringBuilder builder = new StringBuilder(name.length());
        builder.append(name);
        if (name.length() > 0) {
            builder.replace(0, 1, String.valueOf(Character.toUpperCase(name.charAt(0))));
        }
        return builder.toString();
    }
}
