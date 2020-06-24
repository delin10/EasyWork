package nil.ed.easywork.util.httpclient;

import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public interface HttpRequest {

    /**
     * 获取所有首部
     * @return 所有首部
     */
    Map<String, String> getHeaders();

    /**
     * 获取所有查询参数
     * @return 查询参数
     */
    Map<String, String> getParams();

    /**
     * 获取Uri
     * @return uri
     */
    String getUri();

    /**
     * 获取指定名称的首部
     * @param name 名称
     * @return 首部
     */
    String getHeader(String name);

    /**
     * 获取指定名称的查询参数
     * @param name 名称
     * @return 查询参数
     */
    String getParam(String name);

    /**
     * 获取所有Cookie
     * @return 所有Cookie
     */
    Map<String, String> getCookies();

    /**
     * 获取指定名称的Cookie
     * @param name 名称
     * @return cookie值
     */
    String getCookie(String name);

}
