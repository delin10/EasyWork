package nil.ed.easywork.util.httpclient;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public interface HttpClient {

    /**
     * 执行一次请求
     * @param request 请求
     * @return 响应
     */
    HttpResponse exec(HttpRequest request);

}
