package kr.co.hankk20.study.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomSystemException extends RuntimeException implements CustomHttpStatusException {

    @Getter
    private HttpStatus httpStatus;

    public CustomSystemException(String message) {
        super(message);
    }

    public CustomSystemException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
