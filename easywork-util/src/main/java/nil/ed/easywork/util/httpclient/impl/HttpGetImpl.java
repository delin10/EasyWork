package nil.ed.easywork.util.httpclient.impl;

import nil.ed.easywork.util.httpclient.HttpRequest;

import java.util.Map;

/**
 * @author delin10
 * @since 2020/6/23
 **/
public class HttpGetImpl implements HttpRequest {
    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getParam(String name) {
        return null;
    }

    @Override
    public Map<String, String> getCookies() {
        return null;
    }

    @Override
    public String getCookie(String name) {
        return null;
    }
}
