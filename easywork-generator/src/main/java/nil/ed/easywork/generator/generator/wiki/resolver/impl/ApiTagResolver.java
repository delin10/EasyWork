package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import nil.ed.easywork.generator.generator.wiki.bean.ApiBean;
import nil.ed.easywork.generator.generator.wiki.bean.CompositeApiBean;
import nil.ed.easywork.generator.generator.wiki.bean.ParamsContainer;
import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.generator.generator.wiki.resolver.Resolver;
import nil.ed.easywork.generator.generator.wiki.resolver.support.ResolverSupport;
import nil.ed.easywork.util.FlowUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
class ApiTagResolver implements Resolver {

    private static final List<AbstractParamsResolver> PARAMS_RESOLVERS = new LinkedList<>();

    {
        PARAMS_RESOLVERS.add(new ParamsResolver());
        PARAMS_RESOLVERS.add(new RequestBodyResolver());
        PARAMS_RESOLVERS.add(new ResponseBodyResolver());
    }

    @Override
    public String selector() {
        return "api";
    }

    @Override
    public ResolveResult resolve(Elements es) {
        CompositeApiBean compositeApiBean = new CompositeApiBean();
        compositeApiBean.getApiBeanList().addAll(FlowUtils.continueStreamIfNotNull(es)
                .map(e -> {
                    ApiBean apiBean = ResolverSupport.attrAsObject(e, ApiBean.class);
                    PARAMS_RESOLVERS.forEach(resolver -> {
                        Elements elements = e.select(resolver.selector());
                        if (CollectionUtils.isNotEmpty(elements)) {
                            ResolveResult resolveResult = resolver.resolve(elements);
                            if (resolveResult != null) {
                                ParamsContainer container = (ParamsContainer) resolveResult;
                                container.setParent(apiBean);
                                apiBean.getContainers().add(container);
                            }
                        }
                    });
                    return apiBean;
                }).collect(Collectors.toList()));
        return compositeApiBean;
    }
}
