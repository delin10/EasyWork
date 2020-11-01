package nil.ed.easywork.source.parser.processor.test;

/**
 * @author lidelin.
 */
public @interface TestAnno {

    int value() default 123456;

    NestedAnno[] annos() default @NestedAnno;

}
