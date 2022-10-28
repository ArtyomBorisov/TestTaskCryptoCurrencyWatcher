package watcher.controller.advice;

public class ResponseError {
    private String logref;
    private String message;

    public ResponseError(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public String getMessage() {
        return message;
    }
}
