package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import com.google.common.collect.Sets;
import lombok.Data;
import nil.ed.easywork.generator.generator.wiki.bean.ParamBean;
import nil.ed.easywork.generator.generator.wiki.bean.ParamsContainer;
import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.generator.generator.wiki.enums.ParamType;
import nil.ed.easywork.generator.generator.wiki.resolver.Resolver;
import nil.ed.easywork.generator.generator.wiki.resolver.support.ResolverSupport;
import nil.ed.easywork.util.FlowUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public abstract class AbstractParamsResolver implements Resolver {

    private static final List<ThMatcher> MATCHERS = new LinkedList<>();

    static {
        MATCHERS.add(new ThMatcher("required", "是否必须", "是否必需", "必须", "必需", "require"));
        MATCHERS.add(new ThMatcher("name", "参数名称", "参数", "名称", "name"));
        MATCHERS.add(new ThMatcher("defaultValue", "默认值", "default"));
        MATCHERS.add(new ThMatcher("type", "类型"));
        MATCHERS.add(new ThMatcher("comment", "描述", "Note", "note", "说明"));
    }

    @Override
    public ResolveResult resolve(Elements es) {
        return FlowUtils.continueStreamIfNotNull(es)
                    .findFirst()
                    .map(e -> {
                        ParamsContainer container = ResolverSupport.attrAsObject(e, ParamsContainer.class);
                        tryToInit(container, e);
                        container.setType(getType());
                        return container;
                    }).orElse(null);
    }

    /**
     * 参数类型.
     * @return 参数类型.
     */
    protected abstract ParamType getType();

    private void tryToInit(ParamsContainer container, Element e) {
        FlowUtils.continueIfNotNull(e.selectFirst("table"))
                .ifPresent(table -> {
                    Elements ths = table.select("tbody > tr > th.confluenceTh");
                    List<String> paramList = ths.stream()
                            .map(this::resolveTh)
                            .collect(Collectors.toList());
                    Elements trs = table.select("tbody > tr");
                    for (Element trElem : trs) {
                        Iterator<String> paramIt = paramList.iterator();
                        Elements tdElems = trElem.select("td.confluenceTd");
                        if (CollectionUtils.isEmpty(tdElems)) {
                            continue;
                        }
                        Iterator<Element> tdIt = tdElems.iterator();
                        ParamBean paramBean = container.initAndAddParam();
                        while (paramIt.hasNext() && tdIt.hasNext()) {
                            String param = paramIt.next();
                            Element td = tdIt.next();
                            setFieldQuietly(paramBean, param, td.text());
                        }
                    }
                });
    }

    private String resolveTh(Element e) {
        return FlowUtils.continueIfNotNull(e)
                .map(x -> MATCHERS.stream()
                            .filter(m -> m.match(x.text()))
                            .findFirst()
                            .map(ThMatcher::getParam)
                            .orElse(""))
                .orElse(null);
    }

    private static final Set<String> NO_SET = Sets.newHashSet("否", "不", "非", "no", "No", "NO");
    public static void setFieldQuietly(Object obj, String field, Object v) {
        try {
            Field f = FieldUtils.getField(obj.getClass(), field, true);
            if (f.getType().equals(Boolean.class) || f.getType().equals(boolean.class)) {
                f.set(obj, parseBool(v));
            } else {
                f.set(obj, v.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Boolean parseBool(Object v) {
        if (v == null) {
            return Boolean.FALSE;
        }

        String vStr = v.toString();
        return NO_SET.stream().anyMatch(vStr::contains);
    }

    @Data
    private static class ThMatcher {

        private String param;

        private Set<String> containSet = new HashSet<>();

        public ThMatcher(String param, String...containWord) {
            this.param = param;
            Collections.addAll(containSet, containWord);
        }

        public boolean match(String text) {
            return StringUtils.isNotBlank(text)
                    && containSet.stream().anyMatch(text::contains);
        }

    }

}
