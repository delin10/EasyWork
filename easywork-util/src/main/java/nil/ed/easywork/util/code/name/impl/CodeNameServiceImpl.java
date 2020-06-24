package nil.ed.easywork.util.code.name.impl;

import nil.ed.easywork.util.code.name.CodeNameService;
import nil.ed.easywork.util.naming.NamingTranslatorSingleton;
import nil.ed.easywork.util.seg.Segmenter;
import nil.ed.easywork.util.seg.impl.WordSegmenterImpl;
import nil.ed.easywork.util.trans.TranslationStrategy;
import nil.ed.easywork.util.trans.TranslationStrategySingleton;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author delin10
 * @since 2020/6/24
 **/
public class CodeNameServiceImpl implements CodeNameService {

    private List<TranslationStrategy> strategies = Arrays.asList(TranslationStrategySingleton.values());
    private Segmenter segmenter = new WordSegmenterImpl();
    @Override
    public Set<String> name(String src) {
        Set<String> names = new HashSet<>();
        String discardSpaceSrc = src.replaceAll("\\s+", "");
        for (TranslationStrategy strategy : strategies) {
            List<String> result = strategy.trans(discardSpaceSrc);
            result.forEach(r -> {
                String underLineName = String.join("_", cutPunctuation(r).split("\\s+")).toLowerCase();
                names.add(underLineName);
            });
        }
        List<String> words = new LinkedList<>();
        String[] spaceSegs = src.split("\\s+");
        if (spaceSegs.length > 1) {
            Collections.addAll(words, spaceSegs);
        }

        for (TranslationStrategy strategy : strategies) {
            List<StringBuilder> builders = new LinkedList<>();
            boolean head = true;
            for (String word : words) {
                List<String> result = strategy.trans(word);
                for (String tranResult : result) {
                    String underLineName = String.join("_", cutPunctuation(tranResult).split("\\s+")).toLowerCase();
                    if (!head) {
                        builders.forEach(b -> {
                            b.append('_');
                            b.append(underLineName);
                        });
                    } else {
                        builders.add(new StringBuilder(underLineName));
                    }
                }
                head = false;
            }

        }

        return names;
    }

    public static String cutPunctuation(String str) {
        if (!Character.isLetterOrDigit(str.charAt(str.length() - 1))) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static void main(String[] args) {
        CodeNameService nameService = new CodeNameServiceImpl();

        String[] names = {
                "价格",
                "什么 价格",
                "what 价格",
                "广告位"
        };
        for (String name : names) {
            System.out.println(name + " : " + nameService.name(name));
        }
    }
}
