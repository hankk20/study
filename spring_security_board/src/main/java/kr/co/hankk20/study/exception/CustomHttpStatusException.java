package kr.co.hankk20.study.exception;

import org.springframework.http.HttpStatus;

public interface CustomHttpStatusException {

    HttpStatus getHttpStatus();
}
