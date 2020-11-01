package nil.ed.easywork.source.obj.type;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author lidelin.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ImportItem {

    public static final String WILDCARD = "*";

    private String content;

    private boolean isWildcard;

    private boolean isStatic;

    public ImportItem(String content) {
        this.content = content;

    }

    private void resolveContent() {
        assert content != null;
        assert !content.isEmpty();
        if (content.endsWith(WILDCARD)) {
            isWildcard = true;
        }
    }
}
