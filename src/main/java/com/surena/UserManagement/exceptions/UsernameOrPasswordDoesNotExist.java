package com.surena.UserManagement.exceptions;

public class UsernameOrPasswordDoesNotExist extends RuntimeException{

    public UsernameOrPasswordDoesNotExist() {
        super();
    }

    public UsernameOrPasswordDoesNotExist(String message) {
        super(message);
    }

    public UsernameOrPasswordDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameOrPasswordDoesNotExist(Throwable cause) {
        super(cause);
    }

    protected UsernameOrPasswordDoesNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
