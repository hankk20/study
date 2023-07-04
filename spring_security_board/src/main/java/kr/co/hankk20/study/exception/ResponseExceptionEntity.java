package kr.co.hankk20.study.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ResponseExceptionEntity {
    private String message;
    private int statusCode;
    private LocalDateTime timestemp;

    public static ResponseExceptionEntityBuilder builder(){
        return ResponseExceptionEntityBuilder.aResponseExceptionEntity();
    }


    public static final class ResponseExceptionEntityBuilder {
        private String message;
        private int statusCode;
        private LocalDateTime timestemp;

        private ResponseExceptionEntityBuilder() {
        }

        public static ResponseExceptionEntityBuilder aResponseExceptionEntity() {
            return new ResponseExceptionEntityBuilder();
        }

        public ResponseExceptionEntityBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseExceptionEntityBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseExceptionEntityBuilder timestemp(LocalDateTime timestemp) {
            this.timestemp = timestemp;
            return this;
        }

        public ResponseEntity<ResponseExceptionEntity> build() {
            ResponseExceptionEntity responseExceptionEntity = new ResponseExceptionEntity();
            responseExceptionEntity.timestemp = this.timestemp;
            responseExceptionEntity.message = this.message;
            responseExceptionEntity.statusCode = this.statusCode;
            return ResponseEntity.status(this.statusCode)
                    .body(responseExceptionEntity);
        }
    }
}
