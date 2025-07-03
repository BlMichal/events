package com.blazek.events.exceptions;

public class QrCodeGeneraionException extends RuntimeException {

    public QrCodeGeneraionException() {
    }

    public QrCodeGeneraionException(String message) {
        super(message);
    }

    public QrCodeGeneraionException(String message, Throwable cause) {
        super(message, cause);
    }

    public QrCodeGeneraionException(Throwable cause) {
        super(cause);
    }

    public QrCodeGeneraionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
