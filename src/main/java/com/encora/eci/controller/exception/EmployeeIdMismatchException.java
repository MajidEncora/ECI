package com.encora.eci.controller.exception;

public class EmployeeIdMismatchException extends RuntimeException {

    public EmployeeIdMismatchException() {
        super();
    }

    public EmployeeIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmployeeIdMismatchException(final String message) {
        super(message);
    }

    public EmployeeIdMismatchException(final Throwable cause) {
        super(cause);
    }
}