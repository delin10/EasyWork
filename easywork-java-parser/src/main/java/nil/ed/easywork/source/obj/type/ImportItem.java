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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImportItem that = (ImportItem) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
