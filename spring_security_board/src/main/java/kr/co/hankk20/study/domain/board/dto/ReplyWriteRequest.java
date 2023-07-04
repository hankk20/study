package kr.co.hankk20.study.domain.board.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter @ToString
public class ReplyWriteRequest implements Serializable {
    private String contents;
}
