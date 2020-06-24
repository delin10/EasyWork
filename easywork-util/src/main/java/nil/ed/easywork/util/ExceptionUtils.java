package nil.ed.easywork.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @author delin10
 * @since 2020/6/24
 **/
public class ExceptionUtils {

    public static void logErrorAndRethrowRuntime(Throwable e, Logger logger) {
        logger.error("", e);
        throw new RuntimeException(e);
    }

}
