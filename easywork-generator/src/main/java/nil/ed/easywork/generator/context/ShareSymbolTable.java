package nil.ed.easywork.generator.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public class ShareSymbolTable {

    private List<String> table = new ArrayList<>();

    public int register(String symbol) {
        int i = table.size();
        table.add(symbol);
        return i;
    }

    public String indexAt(int i) {
        if (i < table.size()) {
            return table.get(i);
        }

        return null;
    }

}
