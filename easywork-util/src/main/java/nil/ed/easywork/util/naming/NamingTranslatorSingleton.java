package nil.ed.easywork.util.naming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nil.ed.easywork.util.naming.impl.*;

/**
 * @author delin10
 * @since 2020/6/1
 **/
@Getter
@AllArgsConstructor
public enum NamingTranslatorSingleton implements INamingTranslator {
    /**
     * 命名转换器单例模式
     */
    UNDERLINE_TO_CAMEL(new UnderlineToCamelCaseNamingTranslator()),
    UNDERLINE_TO_PASCAL(new UnderlineToPascalNamingTranslator()),
    CAMEL_TO_UNDERLINE(new CamelToUnderlineNamingStrategy()),
    PASCAL_TO_CAMEL(new PascalToCamelNamingStrategy()),
    CAMEL_TO_PASCAL(new CamelToPascalNamingTranslator());
    private INamingTranslator translator;

    @Override
    public String trans(String name) {
        return translator.trans(name);
    }

}
