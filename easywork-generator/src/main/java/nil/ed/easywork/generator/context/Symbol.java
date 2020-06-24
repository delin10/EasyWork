package nil.ed.easywork.generator.context;

/**
 * @author delin10
 * @since 2020/6/2
 **/
public class Symbol {

    private ShareSymbolTable table;

    private int index;

    public Symbol(ShareSymbolTable table, int index) {
        this.table = table;
        this.index = index;
    }

    public String getName() {
        return table.indexAt(index);
    }

}
