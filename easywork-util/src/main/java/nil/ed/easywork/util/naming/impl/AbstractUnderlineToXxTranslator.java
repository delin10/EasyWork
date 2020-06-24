package nil.ed.easywork.util.naming.impl;

import nil.ed.easywork.util.naming.INamingTranslator;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public abstract class AbstractUnderlineToXxTranslator implements INamingTranslator {


    @Override
    public String trans(String name) {
        StringBuilder builder = new StringBuilder(name.length());
        boolean transUpper = false;
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch == '_') {
                transUpper = true;
                continue;
            }

            if (transUpper(transUpper, i, name)) {
                ch = Character.toUpperCase(ch);
                transUpper = false;
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    protected abstract boolean transUpper(boolean transUpper, int index, String origin);

}
