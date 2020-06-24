package nil.ed.easywork.sql.exception;

/**
 * @author delin10
 * @since 2020/5/19
 **/
public class MyException extends RuntimeException{

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String messageTemplate, Object...args) {
        super(String.format(messageTemplate, args));
    }

}
