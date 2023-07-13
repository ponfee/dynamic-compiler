package cn.ponfee.commons.compile.exception;

/**
 * 编译异常
 */
public class CompileExprException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CompileExprException(String message){
        super(message);
    }

    public CompileExprException(String message, Throwable cause){
        super(message, cause);
    }
}
