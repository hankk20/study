package kr.co.hankk20.study.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class BoardWriteRequest {
    @NotBlank
    String contents;
    @NotBlank
    String title;
}
