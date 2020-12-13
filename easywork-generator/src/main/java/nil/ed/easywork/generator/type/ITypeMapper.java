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

    /**
     * 完整数据库类型名称.
     * @param type 类型.
     * @return 完整数据库类型名称.
     */
    String fullDbTypeName(String type);

}
