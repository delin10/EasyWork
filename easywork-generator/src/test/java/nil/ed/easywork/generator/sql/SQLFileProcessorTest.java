package nil.ed.easywork.generator.sql;

import org.junit.Test;

import static org.junit.Assert.*;

public class SQLFileProcessorTest {

    private SQLFileProcessor processor = new SQLFileProcessor("D:/test/sql/txt");

    @Test
    public void process() {
        System.out.println(processor.process());
    }
}