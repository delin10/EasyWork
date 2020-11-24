package nil.ed.easywork.generator.generator.wiki.resolver.impl;

import nil.ed.easywork.generator.generator.wiki.bean.BasePackageResolveResult;
import nil.ed.easywork.generator.generator.wiki.bean.ResolveResult;
import nil.ed.easywork.generator.generator.wiki.resolver.Resolver;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.select.Elements;

/**
 * @author lidelin.
 */
public class BasePackageTagResolver implements Resolver {

    @Override
    public ResolveResult resolve(Elements es) {
        if (CollectionUtils.isNotEmpty(es)) {
            BasePackageResolveResult basePackageResolveResult = new BasePackageResolveResult();
            basePackageResolveResult.setText(es.get(0).text().trim());
            return basePackageResolveResult;
        }
        return null;
    }

    @Override
    public String selector() {
        return "base-package";
    }
}
