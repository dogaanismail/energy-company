package org.energycompany.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class CustomResponse<T> {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private HttpStatus httpStatus;

    private Boolean isSuccess;

    public CustomResponse() {
        // no-arg constructor for Jackson
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    public static final CustomResponse<Void> SUCCESS = CustomResponse.<Void>builder()
            .httpStatus(HttpStatus.OK)
            .isSuccess(true)
            .build();

    public static <T> CustomResponse<T> successOf(final T response) {
        return CustomResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true)
                .response(response)
                .build();
    }

    public static <T> CustomResponse<T> failOf(final T response) {
        return CustomResponse.<T>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .isSuccess(false)
                .response(response)
                .build();
    }

}
