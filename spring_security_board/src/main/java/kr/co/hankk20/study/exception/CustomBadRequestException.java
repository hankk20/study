package kr.co.hankk20.study.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomBadRequestException extends CustomSystemException{

    public CustomBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
