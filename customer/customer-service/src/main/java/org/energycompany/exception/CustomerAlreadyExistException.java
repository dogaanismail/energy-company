package org.energycompany.exception;

import java.io.Serial;

public class CustomerAlreadyExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2178948664026920647L;

    private static final String DEFAULT_MESSAGE = """
            Customer already exist!
            """;

    public CustomerAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    public CustomerAlreadyExistException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
