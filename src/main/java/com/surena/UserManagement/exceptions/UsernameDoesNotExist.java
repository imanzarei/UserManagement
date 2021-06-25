package com.surena.UserManagement.exceptions;

public class UsernameDoesNotExist extends RuntimeException{

    public UsernameDoesNotExist() {
        super();
    }

    public UsernameDoesNotExist(String message) {
        super(message);
    }

    public UsernameDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameDoesNotExist(Throwable cause) {
        super(cause);
    }

    protected UsernameDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
