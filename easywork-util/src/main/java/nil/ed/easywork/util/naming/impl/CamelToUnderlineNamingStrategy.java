package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public class CamelToUnderlineNamingStrategy implements INamingTranslator {

    @Override
    public String trans(String name) {
        StringBuilder builder = new StringBuilder();
        if (name.length() <= 1) {
            return name;
        }
        builder.append(name.charAt(0));
        for (int i = 1; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append("_");
                ch = Character.toLowerCase(name.charAt(i));
            }
            builder.append(ch);
        }
        return builder.toString();
    }

}
