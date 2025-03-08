package com.megacitycab.megacitycabservice.exception;

public class MegaCityCabException extends Exception {
    public MegaCityCabException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public MegaCityCabException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage.getMessage(), cause);
    }
}