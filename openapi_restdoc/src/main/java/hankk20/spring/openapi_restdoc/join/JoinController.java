package hankk20.spring.openapi_restdoc.join;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    @PostMapping(value = "/join")
    public ResponseEntity join(@Validated @RequestBody JoinRequest joinRequest){
        return ResponseEntity.ok(new JoinResponse(1L, Status.ENABLE));
    }

}
