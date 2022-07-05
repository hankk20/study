package hankk20.spring.openapi_restdoc.join;

import lombok.Getter;

@Getter
public class JoinResponse {
    private long id;
    private Status status;

    public JoinResponse(long id, Status status) {
        this.id = id;
        this.status = status;
    }
}
