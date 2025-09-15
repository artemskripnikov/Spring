package org.skypro.skyshop;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String massage) {
        super(massage);
    }

    public NoSuchProductException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
