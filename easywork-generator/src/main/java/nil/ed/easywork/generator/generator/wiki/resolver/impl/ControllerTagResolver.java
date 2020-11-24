package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import com.google.common.base.MoreObjects;
import nil.ed.easywork.generator.generator.wiki.bean.CompositeApiBean;
import nil.ed.easywork.generator.generator.wiki.bean.ControllerBean;
import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;
import nil.ed.easywork.generator.generator.wiki.resolver.Resolver;
import nil.ed.easywork.generator.generator.wiki.resolver.support.ResolverSupport;
import nil.ed.easywork.util.FlowUtils;
import org.jsoup.select.Elements;

/**
 * @author lidelin.
 */
public class ControllerTagResolver implements Resolver {

    private ApiTagResolver apiTagResolver = new ApiTagResolver();

    @Override
    public String selector() {
        return "controller";
    }

    @Override
    public ResolveResult resolve(Elements es) {
        return FlowUtils.continueStreamIfNotNull(es)
                .findFirst()
                .map(e -> {
                    ControllerBean controllerBean = ResolverSupport.attrAsObject(e, ControllerBean.class);
                    String basePkg = (String) ResolveContext.CXT.getOrDefault(ResolveContext.BASE_PACKAGE_PARAM, "");
                    controllerBean.setBasePackage(basePkg);
                    controllerBean.setPath(MoreObjects.firstNonNull(controllerBean.getPath(), ""));
                    Elements apiTags = e.select(apiTagResolver.selector());
                    ResolveResult resolveResult = apiTagResolver.resolve(apiTags);
                    if (resolveResult != null) {
                        CompositeApiBean compositeApiBean = (CompositeApiBean) resolveResult;
                        compositeApiBean.getApiBeanList().forEach(api -> api.setParent(controllerBean));
                        controllerBean.getApis().addAll(compositeApiBean.getApiBeanList());
                    }
                    return controllerBean;
                }).orElse(null);
    }
}
