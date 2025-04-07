package org.energycompany.exception;

import java.io.Serial;


public class MeteringPointNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5854010258697200749L;

    private static final String DEFAULT_MESSAGE = """
            Metering point not found!
            """;

    public MeteringPointNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public MeteringPointNotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
