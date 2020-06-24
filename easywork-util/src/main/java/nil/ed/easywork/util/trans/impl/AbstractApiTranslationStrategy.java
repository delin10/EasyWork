package nil.ed.easywork.util.trans.impl;

import nil.ed.easywork.util.trans.TranslationStrategy;
import nil.ed.easywork.util.httpclient.HttpClientsX;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public abstract class AbstractApiTranslationStrategy implements TranslationStrategy {

    private String url;

    protected CookieStore store;

    AbstractApiTranslationStrategy(String url) {
        this.url = url;
        this.store = new BasicCookieStore();
    }

    @Override
    public List<String> trans(String chineseName) {
        List<String> names = new LinkedList<>();
        try (CloseableHttpClient client = HttpClientsX.getClient(store)) {
            HttpUriRequest request = buildRequest(chineseName);
            HttpResponse response = client.execute(request);
            processResponse(response, names);
            return Collections.unmodifiableList(names);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建请求
     * @param chineseName 中文单词
     * @return 请求
     */
    protected abstract HttpUriRequest buildRequest(String chineseName);

    /**
     * 处理响应
     * @param response 响应
     */
    protected abstract void processResponse(HttpResponse response, List<String> names);

}
