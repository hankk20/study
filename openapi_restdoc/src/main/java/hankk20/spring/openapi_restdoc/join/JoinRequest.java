package hankk20.spring.openapi_restdoc.join;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

@Getter
@Setter
public class JoinRequest {


    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
    @NotBlank @Pattern(regexp = "[0-9]+")
    private String phoneNumber;
    @NotNull
    private Position position;

    @Size(max = 100)
    private String address;

}
