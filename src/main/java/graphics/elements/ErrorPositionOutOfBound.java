package graphics.elements;

public class ErrorPositionOutOfBound extends RuntimeException{

    public ErrorPositionOutOfBound(Position p){
        System.out.println(p + " is out of bound");
    }
    public ErrorPositionOutOfBound() {
    }

    public ErrorPositionOutOfBound(String message) {
        super(message);
    }

    public ErrorPositionOutOfBound(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorPositionOutOfBound(Throwable cause) {
        super(cause);
    }

    public ErrorPositionOutOfBound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
