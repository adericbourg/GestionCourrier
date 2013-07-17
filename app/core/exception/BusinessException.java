package core.exception;

/**
 * @author alban
 */
public abstract class BusinessException extends RuntimeException {

    private final String message;

    protected BusinessException(String message) {
        this.message = message;
    }

    /**
     * Exception message.
     * 
     * @return Message.
     */
    public final String getMessage() {
        return message;
    }
}
