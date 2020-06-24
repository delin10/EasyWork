package nil.ed.easywork.util.code.name;

import nil.ed.easywork.util.httpclient.HttpClient;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public interface HttpClientProvider {

    /**
     * 提供一个HttpClient实例
     * @return 结果
     */
    HttpClient provide();

}
