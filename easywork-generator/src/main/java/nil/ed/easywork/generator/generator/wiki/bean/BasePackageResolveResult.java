package nil.ed.easywork.generator.generator.wiki.bean;

import lombok.Data;

/**
 * @author lidelin.
 */
@Data
public class BasePackageResolveResult extends AbstractTextResolveResult {

    @Override
    public String getKey() {
        return "basePackage";
    }

}
