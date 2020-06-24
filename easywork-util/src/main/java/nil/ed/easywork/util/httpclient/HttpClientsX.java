package nil.ed.easywork.util.httpclient;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public class HttpClientsX {

    private HttpClientsX(){}

    public static CloseableHttpClient getDefaultClient() {
        return HttpClients.createDefault();
    }

    public static CloseableHttpClient getClient(CookieStore store) {
        return HttpClients.custom().setDefaultCookieStore(store).build();
    }

}
