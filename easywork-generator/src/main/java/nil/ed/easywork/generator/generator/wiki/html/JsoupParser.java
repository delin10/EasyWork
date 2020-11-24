package nil.ed.easywork.generator.generator.wiki.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author lidelin.
 */
public class JsoupParser {

    public Document parse(String html) {
        return Jsoup.parse(html);
    }

    public static void main(String[] args) {
        JsoupParser parser = new JsoupParser();
        Document doc = parser.parse("<base-package></base-package>\n" +
                "<controller id=\"\" base-path=\"\">\n" +
                "    <api id=\"\" path=\"\">\n" +
                "        <params>\n" +
                "            <table>\n" +
                "            </table>\n" +
                "        </params>\n" +
                "        <request-body id=\"\">\n" +
                "        </request-body>\n" +
                "        <response id=\"\">\n" +
                "        </response>\n" +
                "    </api>\n" +
                "</controller>");
        Elements e = doc.select("base-package");
        System.out.println(e);
    }

}
