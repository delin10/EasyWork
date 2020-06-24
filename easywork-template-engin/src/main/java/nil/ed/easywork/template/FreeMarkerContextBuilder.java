package nil.ed.easywork.template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public class FreeMarkerContextBuilder implements ContextBuilder<Object> {

    private Map<String, Object> map = new HashMap<>();

    @Override
    public ContextBuilder<Object> put(String name, Object obj) {
        map.put(name, obj);
        return this;
    }

    @Override
    public Object getContext() {
        return map;
    }

}
