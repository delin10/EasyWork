import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author delin10
 * @since 2020/6/4
 **/
public class Test {

    public static void main(String[] args) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        Template template = Template.getPlainTextTemplate("xxx", "xxx", cfg);
    }

}
