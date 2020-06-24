package nil.ed.easywork.generator.context;

import nil.ed.easywork.generator.sql.obj.TableDetails;

import java.util.Collection;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public interface IContext<E> {

    void put(String name, E e);

    E get(String name);

    Collection<E> getAll();

}
