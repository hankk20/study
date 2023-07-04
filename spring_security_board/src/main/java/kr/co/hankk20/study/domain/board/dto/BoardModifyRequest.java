package kr.co.hankk20.study.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import kr.co.hankk20.study.domain.board.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

@Getter @Setter
public class BoardModifyRequest {
    @JsonUnwrapped
    private BoardWriteRequest boardWriteRequest;

    //수정가능 항목을 셋팅하는 부분을 캡슐화
    public Board update(Board board){
        board.setTitle(boardWriteRequest.getTitle());
        board.setContents(boardWriteRequest.getContents());
        return board;
    }
}
