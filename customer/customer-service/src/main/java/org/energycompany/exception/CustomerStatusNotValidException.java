package org.energycompany.exception;

import java.io.Serial;

public class CustomerStatusNotValidException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3440177088502218750L;

    private static final String DEFAULT_MESSAGE = """
            Customer status is not valid!
            """;

    public CustomerStatusNotValidException() {
        super(DEFAULT_MESSAGE);
    }

    public CustomerStatusNotValidException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
