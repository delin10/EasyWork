package nil.ed.easywork.util.trans.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nil.ed.easywork.util.ExceptionUtils;
import nil.ed.easywork.util.httpclient.HttpClientsX;
import nil.ed.easywork.util.httpclient.response.ResponseUtils;
import nil.ed.easywork.util.js.JsFunctionExecutor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author delin10
 * @since 2020/6/23
 **/
@Slf4j
public class BaiduApiTranslationStrategy extends AbstractApiTranslationStrategy {

    private static final String BAIDU_TRANS_URL = "https://fanyi.baidu.com/v2transapi";
    private static final String BAIDU_TRANS_URL_FORMATTER = "https://fanyi.baidu.com/v2transapi?from=zh&to=en";
    private static final JsFunctionExecutor JS_EXEC = new JsFunctionExecutor();
    private static final String SIGN_FUNC_NAME = "sign";

    static {
        try {
            JS_EXEC.loadScript(BaiduApiTranslationStrategy.class.getResource("/js/baidu.js").getFile());
        } catch (Exception e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        }
    }

    public BaiduApiTranslationStrategy() {
        super(BAIDU_TRANS_URL);
    }

    @Override
    protected HttpUriRequest buildRequest(String chineseName) {
        try {
            // todo 获取 token
            HttpPost post = new HttpPost();
            post.setURI(new URI(BAIDU_TRANS_URL_FORMATTER));
            PreObject commonObject = getPreObject();
            System.out.println(commonObject);

            post.setEntity(buildUrlEncodedForm(chineseName, commonObject.token));
            return post;
        } catch (URISyntaxException | NoSuchMethodException | ScriptException e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        } catch (UnsupportedEncodingException ignore) { }
        return null;
    }

    private UrlEncodedFormEntity buildUrlEncodedForm(String chineseName, String token) throws ScriptException, NoSuchMethodException, UnsupportedEncodingException {
        List<NameValuePair> pairs = new LinkedList<>();
        pairs.add(new BasicNameValuePair("from", "zh"));
        pairs.add(new BasicNameValuePair("to", "en"));
        pairs.add(new BasicNameValuePair("query", chineseName));
        pairs.add(new BasicNameValuePair("transtype", "translang"));
        pairs.add(new BasicNameValuePair("simple_means_flag", "3"));
        pairs.add(new BasicNameValuePair("sign", "" + JS_EXEC.invoke(SIGN_FUNC_NAME, chineseName)));
        pairs.add(new BasicNameValuePair("token", token));
        pairs.add(new BasicNameValuePair("domain", "common"));
        return new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8);
    }

    @Override
    protected void processResponse(HttpResponse response, List<String> names) {
        try {
            String res = ResponseUtils.readTextResponse(response);
            JSONObject jsonObject = JSON.parseObject(res);
            JSONPath path = JSONPath.compile("$.trans_result.data[0].dst");
            String result = (String) path.eval(jsonObject);
            if (result != null) {
                names.add(result);
            }
        } catch (IOException e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        }
    }
    private static final String TOKEN_URI = "https://fanyi.baidu.com/?aldtype=16047";
    private static final Pattern TOKEN_PATTERN = Pattern.compile("token:\\s*'(?<token>.*?)'|window\\s*\\[\\s*'yjs_js_challenge_token'\\s*]\\s*=\\s*'(?<challengeToken>.*?)'");
    private PreObject getPreObject() {
        try (CloseableHttpClient client = HttpClientsX.getClient(store)) {
            HttpGet get = new HttpGet();
            get.setURI(new URI(TOKEN_URI));
            // 第一次获取BAIDUID
            client.execute(get);
            // 第二次获取BAIDUID对应的的token
            HttpResponse response = client.execute(get);
            String result = ResponseUtils.readTextResponse(response);
            Matcher matcher = TOKEN_PATTERN.matcher(result);
            PreObject preObject = new PreObject();
            while (matcher.find()) {
                if (matcher.group("token") != null) {
                    preObject.token = matcher.group("token");
                } else if (matcher.group("challengeToken") != null) {
                    preObject.yjsJsChallengeToken = matcher.group("challengeToken");
                }
            }
            return preObject;
        } catch (IOException | URISyntaxException e) {
            ExceptionUtils.logErrorAndRethrowRuntime(e, log);
        }
        return null;
    }


    public static void main(String[] args) throws Exception{
        BaiduApiTranslationStrategy strategy = new BaiduApiTranslationStrategy();
        System.out.println(strategy.trans("你是个这样的杀掉"));
    }

    @Getter
    @Setter
    @ToString
    private static class PreObject {

        private String token;

        private String yjsJsChallengeToken;

    }
}

