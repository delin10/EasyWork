package nil.ed.easywork.util.trans.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.ExceptionUtils;
import nil.ed.easywork.util.httpclient.response.ResponseUtils;
import nil.ed.easywork.util.js.JsFunctionExecutor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author delin10
 * @since 2020/6/24
 **/
@Slf4j
public class GoogleApiTranslationStrategy extends AbstractApiTranslationStrategy {

    private static final String GOOGLE_TRANS_URL = "https://translate.google.cn/translate_a/single";
    private static final String[] DTS = {"at", "bd", "ex", "ld", "md", "qca", "rw", "rm", "sos", "ss", "t"};
    private static final Map<String, Object> QUERY_CONTEXT = new HashMap<>(16, 1);
    private static final JsFunctionExecutor JS_EXEC = new JsFunctionExecutor();
    private static final String SIGN_FUNC = "ru";
    static {
        try {
            JS_EXEC.loadScript(BaiduApiTranslationStrategy.class.getResource("/js/google.js").getFile());
        } catch (Exception e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        }
    }

    static {
        QUERY_CONTEXT.put("dt", DTS);
        QUERY_CONTEXT.put("client", "webapp");
        QUERY_CONTEXT.put("sl", "zh-CN");
        QUERY_CONTEXT.put("tl", "en");
        QUERY_CONTEXT.put("hl", "zh-CN");
        QUERY_CONTEXT.put("source", "bh");
        QUERY_CONTEXT.put("ssel", "0");
        QUERY_CONTEXT.put("tsel", "0");
        QUERY_CONTEXT.put("xid", "45662847");
        QUERY_CONTEXT.put("kc", "1");
    }

    public GoogleApiTranslationStrategy() {
        super(GOOGLE_TRANS_URL);
    }
//
    @Override
    protected HttpUriRequest buildRequest(String chineseName) {
        return new HttpGet(buildUrl(chineseName));
    }

    private String buildUrl(String src) {
        return GoogleApiTranslationStrategy.GOOGLE_TRANS_URL + "?" + buildQueryParam(src);
    }

    @SuppressWarnings("unchecked")
    private String buildQueryParam(String src) {
        try {
            QUERY_CONTEXT.put("q", URLEncoder.encode(src, StandardCharsets.UTF_8.name()));
            QUERY_CONTEXT.put("tk", JS_EXEC.invoke(SIGN_FUNC, src));
            StringBuilder builder = new StringBuilder(1024);
            QUERY_CONTEXT.forEach((k, v) -> {
                if (v == null) {
                    return;
                }
                if (v instanceof Collection) {
                    Collection values = (Collection) v;
                    String param = values.stream()
                            .map(x -> k + "=" + x)
                            .collect(Collectors.joining("&")).toString();
                    builder.append(param);
                    builder.append('&');
                } else if (v.getClass().isArray()) {
                    int length = Array.getLength(v);
                    for (int i = 0; i < length; ++i) {
                        builder.append(k);
                        builder.append("=");
                        builder.append(Array.get(v, i));
                        builder.append('&');
                    }
                } else {
                    builder.append(k);
                    builder.append("=");
                    builder.append(v);
                    builder.append("&");
                }
            });
            if (builder.length() > 0) {
                builder.delete(builder.length() - 1, builder.length());
            }
            return builder.toString();
        } catch (UnsupportedEncodingException ignore) {
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final JSONPath PATH = JSONPath.compile("$[0][0][0]");
    @Override
    protected void processResponse(HttpResponse response, List<String> names) {
        try {
            String json = ResponseUtils.readTextResponse(response);
            Object result = PATH.eval(JSON.parseArray(json));
            if (result != null) {
                names.add(result.toString());
            }
        } catch (IOException e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        }
    }

    public static void main(String[] args) {
        GoogleApiTranslationStrategy nameStrategy = new GoogleApiTranslationStrategy();
        System.out.println(nameStrategy.trans("你是杀掉"));
    }

}
