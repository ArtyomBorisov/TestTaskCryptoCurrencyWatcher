package watcher.exception;

public class CustomException extends NullPointerException {
    public CustomException() {
    }

    public CustomException(String s) {
        super(s);
    }
}
