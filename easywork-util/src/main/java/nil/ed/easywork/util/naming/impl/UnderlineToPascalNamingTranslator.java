package nil.ed.easywork.util.naming.impl;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public class UnderlineToPascalNamingTranslator extends AbstractUnderlineToXxTranslator {

    @Override
    protected boolean transUpper(boolean transUpper, int index, String origin) {
        return transUpper || index == 0;
    }
}
