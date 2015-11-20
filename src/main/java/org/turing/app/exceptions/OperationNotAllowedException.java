package org.turing.app.exceptions;

public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException() {
    }

    public OperationNotAllowedException(String message) {
        super(message);
    }
}
