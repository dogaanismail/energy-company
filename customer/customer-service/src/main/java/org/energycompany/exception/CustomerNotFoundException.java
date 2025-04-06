package org.energycompany.exception;

import java.io.Serial;

public class CustomerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3952215105519401565L;

    private static final String DEFAULT_MESSAGE = """
            Customer not found!
            """;

    public CustomerNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CustomerNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
