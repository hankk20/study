package kr.co.hankk20.study.domain.board.controller;


import com.querydsl.core.types.Predicate;
import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.board.Board;
import kr.co.hankk20.study.domain.board.dto.BoardDto;
import kr.co.hankk20.study.domain.board.dto.BoardModifyRequest;
import kr.co.hankk20.study.domain.board.dto.BoardWriteRequest;
import kr.co.hankk20.study.domain.board.repository.BoardRepository;
import kr.co.hankk20.study.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/board")
    public ResponseEntity<Page<BoardDto>> boardList(@AuthenticationPrincipal(expression = "account") Account account,
                                                    @QuerydslPredicate(root = Board.class) Predicate predicate, Pageable pageable){
        Page<BoardDto> allBoard = boardRepository.findAllBoard(account.getId(), predicate, pageable);
        return ResponseEntity.ok(allBoard);
    }

    @PostMapping("/board")
    public ResponseEntity<Long> write(@AuthenticationPrincipal(expression = "account") Account account,
                                      @RequestBody @Validated BoardWriteRequest request){
        return ResponseEntity.ok(boardService.write(account, request));
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDto> board(@AuthenticationPrincipal(expression = "account")Account account,
                                          @PathVariable(value = "id", required = true) long id){
        return ResponseEntity.ok(boardRepository.findBoard(id, account.getId()));
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<Long> update(@AuthenticationPrincipal(expression = "account") Account account,
                                       @PathVariable("id") long id, @RequestBody BoardModifyRequest request){
        return ResponseEntity.ok(boardService.update(id, account, request));
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal(expression = "account") Account account,
                                       @PathVariable("id") long id){
        return ResponseEntity.ok(boardService.delete(account, id));
    }

    @GetMapping("/board/lock")
    public ResponseEntity<Long> lock(){
        boardService.lock();
        return ResponseEntity.ok().build();
    }

    public static void main(String[] args) {
        List<Object> objects = Arrays.asList(null);
        System.out.println(objects);

    }

}
