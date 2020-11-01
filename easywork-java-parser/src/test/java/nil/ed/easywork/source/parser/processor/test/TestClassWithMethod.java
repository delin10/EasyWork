package nil.ed.easywork.source.parser.processor.test;

public class TestClassWithMethod {
    public static void test() {

    }

    public void test(int a) {

    }

    @TestAnno
    public TestClassWithMethod test(@TestAnno double a) {
        return null;
    }


    public TestClassWithMethod test(TestClassWithMethod a) {
        return null;
    }

}
