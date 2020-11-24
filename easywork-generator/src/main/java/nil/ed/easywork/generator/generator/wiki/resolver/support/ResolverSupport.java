package nil.ed.easywork.generator.generator.wiki.resolver.support;

import nil.ed.easywork.util.ClassUtils;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class ResolverSupport {

    public static <T> T attrAsObject(Element e, Class<T> clazz) {
        Map<String, Object> attrs = e.attributes().asList()
                .stream()
                .collect(Collectors.toMap(k -> NamingTranslatorSingleton.MID_LINE_TO_CAMEL.trans(k.getKey()), Attribute::getValue));
        T obj = ClassUtils.init(clazz);
        try {
            BeanUtilsBean2.getInstance().copyProperties(obj, attrs);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
        return obj;
    }

}
