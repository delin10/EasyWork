package nil.ed.easywork.generator.generator.wiki.resolver;

import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;
import nil.ed.easywork.generator.generator.wiki.resolver.impl.BasePackageTagResolver;
import nil.ed.easywork.generator.generator.wiki.resolver.impl.ControllerTagResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
public class ResolverPipeline {

    private static final List<Resolver> resolverList = new LinkedList<>();

    static {
        resolverList.add(new BasePackageTagResolver());
        resolverList.add(new ControllerTagResolver());
    }

    public static void process(String html) {
        Document doc = Jsoup.parse(html);
        resolverList.forEach(r -> {
            Elements elements = doc.select(r.selector());
            if (CollectionUtils.isNotEmpty(elements)) {
                ResolveResult resolveResult = r.resolve(elements);
                if (resolveResult == null) {
                    return;
                }
                resolveResult.register();
            }
        });
    }

    public static void main(String[] args) {
        ResolverPipeline.process(
                "" +
                "<base-package>\n" +
                "   nil.ed.controller\n" +
                "  </base-package> \n" +
                "  <controller id=\"TestController\" path=\"/a\"> \n" +
                "   <api id=\"getA\" path=\"/get\"> \n" +
                "    <params> \n" +
                "     <table class=\"confluenceTable\"> \n" +
                "      <colgroup> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "      </colgroup> \n" +
                "      <tbody> \n" +
                "       <tr> \n" +
                "        <th class=\"confluenceTh\">参数</th> \n" +
                "        <th class=\"confluenceTh\">类型</th> \n" +
                "        <th class=\"confluenceTh\">描述</th> \n" +
                "       </tr> \n" +
                "       <tr> \n" +
                "        <td class=\"confluenceTd\">a<br /></td> \n" +
                "        <td class=\"confluenceTd\">String<br /></td> \n" +
                "        <td class=\"confluenceTd\">描述<br /></td> \n" +
                "       </tr> \n" +
                "      </tbody> \n" +
                "     </table> \n" +
                "    </params> \n" +
                "    <request-body id=\"TestVO\"> \n" +
                "     <table class=\"confluenceTable\"> \n" +
                "      <colgroup> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "      </colgroup> \n" +
                "      <tbody> \n" +
                "       <tr> \n" +
                "        <th class=\"confluenceTh\">参数</th> \n" +
                "        <th class=\"confluenceTh\">类型</th> \n" +
                "        <th class=\"confluenceTh\">描述</th> \n" +
                "       </tr> \n" +
                "       <tr> \n" +
                "        <td class=\"confluenceTd\">a<br /></td> \n" +
                "        <td class=\"confluenceTd\">String<br /></td> \n" +
                "        <td class=\"confluenceTd\">描述<br /></td> \n" +
                "       </tr> \n" +
                "      </tbody> \n" +
                "     </table> \n" +
                "    </request-body> \n" +
                "    <response id=\"TestVOResponse\"> \n" +
                "     <table class=\"confluenceTable\"> \n" +
                "      <colgroup> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "       <col /> \n" +
                "      </colgroup> \n" +
                "      <tbody> \n" +
                "       <tr> \n" +
                "        <th class=\"confluenceTh\">参数</th> \n" +
                "        <th class=\"confluenceTh\">类型</th> \n" +
                "        <th class=\"confluenceTh\">描述</th> \n" +
                "       </tr> \n" +
                "       <tr> \n" +
                "        <td class=\"confluenceTd\">a<br /></td> \n" +
                "        <td class=\"confluenceTd\">String<br /></td> \n" +
                "        <td class=\"confluenceTd\">描述<br /></td> \n" +
                "       </tr> \n" +
                "      </tbody> \n" +
                "     </table> \n" +
                "    </response> \n" +
                "   </api> \n" +
                "  </controller>");
        System.out.println(ResolveContext.CXT);
    }

}
