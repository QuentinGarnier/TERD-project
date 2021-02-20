package World;

public class NO_MORE_SPACE_ERROR extends Exception{
    public NO_MORE_SPACE_ERROR() {
    }

    public NO_MORE_SPACE_ERROR(String message) {
        super(message);
    }

    public NO_MORE_SPACE_ERROR(String message, Throwable cause) {
        super(message, cause);
    }

    public NO_MORE_SPACE_ERROR(Throwable cause) {
        super(cause);
    }

    public NO_MORE_SPACE_ERROR(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
