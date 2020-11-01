package nil.ed.easywork.template.support;

import freemarker.core.Environment;
import freemarker.template.DefaultListAdapter;
import freemarker.template.DefaultMapAdapter;
import freemarker.template.TemplateHashModel;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author lidelin.
 */
public class FreeMarkerSupport {

    private static final Field ENV_ROOT_DATA_MODEL_FIELD;

    private static final Pattern PLACE_HOLDER_PATTERN = Pattern.compile("\\$\\{[\\s\\S]*?}");

    public static boolean hasPlaceHolder(String v) {
        return PLACE_HOLDER_PATTERN.matcher(v).find();
    }

    static {
        ENV_ROOT_DATA_MODEL_FIELD = FieldUtils.getDeclaredField(Environment.class, "rootDataModel", true);
    }

    public static String renderPlaceHolder(String v, TemplateHashModel model) {
        return FreeMarkerTemplateEngineAdapter.INSTANCE.process(v, model);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(String name, Environment env) {
        try {
            return (List<T>) ((DefaultListAdapter) env.getDataModel().get(name)).getWrappedObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getRawContext(Environment env) {
        try {
            TemplateHashModel model = (TemplateHashModel) ENV_ROOT_DATA_MODEL_FIELD.get(env);
            if (model instanceof DefaultMapAdapter) {
                return ((DefaultMapAdapter) model).getWrappedObject();
            }
            throw new IllegalArgumentException("Unsupported Type: " + model.getClass());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
