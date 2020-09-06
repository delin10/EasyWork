package nil.ed.easywork.generator.sql;

import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class SQLFileProcessorTest {

    private SQLFileProcessor processor = new SQLFileProcessor("D:/test/sql/txt", new AdsTypeMapper());

    @Test
    public void process() {
        System.out.println(processor.process());
    }
}