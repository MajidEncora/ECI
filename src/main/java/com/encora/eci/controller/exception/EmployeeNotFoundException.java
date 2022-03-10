package com.encora.eci.controller.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException() {
        super();
    }

    public EmployeeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotFoundException(final String message) {
        super(message);
    }

    public EmployeeNotFoundException(final Throwable cause) {
        super(cause);
    }
}