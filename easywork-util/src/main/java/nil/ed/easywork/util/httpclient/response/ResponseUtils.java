package nil.ed.easywork.util.httpclient.response;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/24
 **/
public class ResponseUtils {

    public static String readTextResponse(HttpResponse response) throws IOException {
        List<String> lines = IOUtils.readLines(response.getEntity().getContent(), StandardCharsets.UTF_8);
        return String.join("\n", lines);
    }

}
