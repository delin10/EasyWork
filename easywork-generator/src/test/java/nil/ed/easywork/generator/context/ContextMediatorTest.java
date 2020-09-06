package nil.ed.easywork.generator.context;

import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContextMediatorTest {
    private SQLFileProcessor processor = new SQLFileProcessor("D:/test/sql.txt", new AdsTypeMapper());
    @Test
    public void sqlContextToJavaContext() {
        SQLContext sqlContext = new SQLContext();
        processor.process()
                .forEach(details -> {
                    sqlContext.put(details.getTableObj().getName(), details);
                });
        ContextMediator mediator = new ContextMediator(sqlContext);
        System.out.println(mediator.sqlContextToJavaContext(sqlContext));
    }
}