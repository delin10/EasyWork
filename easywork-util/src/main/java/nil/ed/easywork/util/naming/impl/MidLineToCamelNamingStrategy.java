package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;

/**
 * @author lidelin.
 */
public class MidLineToCamelNamingStrategy implements INamingTranslator {
    @Override
    public String trans(String name) {
        StringBuilder builder = new StringBuilder(name.length());
        boolean transUpper = false;
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch == '-') {
                transUpper = true;
                continue;
            }

            if (transUpper) {
                ch = Character.toUpperCase(ch);
                transUpper = false;
            }
            builder.append(ch);
        }
        return builder.toString();
    }
}
