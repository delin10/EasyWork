package nil.ed.easywork.generator.type;

/**
 * @author lidelin
 */
public interface ITypeMapper {

    /**
     * DBType -> JavaType
     * @param type DB类型
     * @return Java类型
     */
    String map(String type);

}
