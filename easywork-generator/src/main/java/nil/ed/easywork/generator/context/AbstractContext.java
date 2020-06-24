package nil.ed.easywork.generator.context;

import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/2
 **/
@ToString
public abstract class AbstractContext<E> implements IContext<E> {

    protected Map<String, E> map = new HashMap<>();

    private Map<String, Object> additional = new HashMap<>();

    @Override
    public E get(String name) {
        return map.get(name);
    }

    @Override
    public void put(String name, E e) {
        map.put(name, e);
    }

    @Override
    public Collection<E> getAll() {
        return map.values();
    }

    public Map<String, Object> getAdditional() {
        return additional;
    }

}
