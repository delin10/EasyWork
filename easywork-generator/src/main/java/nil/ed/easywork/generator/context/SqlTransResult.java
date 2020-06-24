package nil.ed.easywork.generator.context;

import lombok.Getter;
import nil.ed.easywork.source.obj.struct.BaseClass;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/3
 **/
@Getter
public class SqlTransResult {

    /**
     * entity name -> table name
     */
    private Map<String, String> nameMap = new HashMap<>();

    private List<BaseClass> entities = new LinkedList<>();

    private List<BaseClass> mappers = new LinkedList<>();

    public void addEntity(BaseClass clazz) {
        entities.add(clazz);
    }

    public void addMapper(BaseClass mapper) {
        mappers.add(mapper);
    }

    public void addMap(String entityName, String tableName) {
        nameMap.put(entityName, tableName);
    }

}
