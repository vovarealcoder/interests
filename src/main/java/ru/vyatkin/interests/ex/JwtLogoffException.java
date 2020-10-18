package ru.vyatkin.interests.ex;

public class JwtLogoffException extends RuntimeException {
    public JwtLogoffException() {
    }

    public JwtLogoffException(String message) {
        super(message);
    }

    public JwtLogoffException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtLogoffException(Throwable cause) {
        super(cause);
    }

    public JwtLogoffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
