package nil.ed.easywork.generator.generator.wiki.bean;

import com.google.common.base.MoreObjects;
import lombok.Data;
import nil.ed.easywork.generator.generator.wiki.context.ResolveContext;

/**
 * @author lidelin.
 */
@Data
public abstract class AbstractTextResolveResult implements ResolveResult {

    private String text;

    @Override
    public Object getResult() {
        return MoreObjects.firstNonNull(text, "");
    }

}
