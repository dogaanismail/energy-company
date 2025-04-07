package org.energycompany.exception;

import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    subErrors.add(
                            CustomError.CustomSubError.builder()
                                    .field(fieldName)
                                    .message(message)
                                    .build()
                    );
                }
        );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Validation failed")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handlePathVariableErrors(ConstraintViolationException constraintViolationException) {

        List<CustomError.CustomSubError> subErrors = new ArrayList<>();
        constraintViolationException.getConstraintViolations()
                .forEach(constraintViolation ->
                        subErrors.add(
                                CustomError.CustomSubError.builder()
                                        .message(constraintViolation.getMessage())
                                        .field(StringUtils.substringAfterLast(constraintViolation.getPropertyPath().toString(), "."))
                                        .value(constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null)
                                        .type(constraintViolation.getInvalidValue().getClass().getSimpleName())
                                        .build()
                        )
                );

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(CustomError.Header.VALIDATION_ERROR.getName())
                .message("Constraint violation")
                .subErrors(subErrors)
                .build();

        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<?> handleRuntimeException(RuntimeException runtimeException) {

        CustomError customError = CustomError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(CustomError.Header.API_ERROR.getName())
                .message(runtimeException.getMessage())
                .build();

        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeteringPointNotFoundException.class)
    public ResponseEntity<CustomError> handleMeteringPointNotFoundException(MeteringPointNotFoundException ex) {

        CustomError error = CustomError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(CustomError.Header.NOT_FOUND.getName())
                .message(ex.getMessage())
                .isSuccess(false)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
