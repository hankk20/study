package kr.co.hankk20.study.domain.board.controller;

import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.board.dto.ReplySearchDto;
import kr.co.hankk20.study.domain.board.dto.ReplyWriteRequest;
import kr.co.hankk20.study.domain.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


    @PostMapping("/board/{id}/reply")
    public ResponseEntity<Long> write(@AuthenticationPrincipal(expression = "account") Account account,
                                      @PathVariable("id")Long id,
                                      @RequestBody ReplyWriteRequest request){
        return ResponseEntity.ok(replyService.write(id, account, request));
    }
    @PutMapping("/board/reply/{id}")
    public ResponseEntity<Long> update(@AuthenticationPrincipal(expression = "account") Account account,
                                      @PathVariable("id")Long id,
                                      @RequestBody ReplyWriteRequest request){
        return ResponseEntity.ok(replyService.update(id, request));
    }
    @DeleteMapping("/board/reply/{id}")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal(expression = "account") Account account,
                                       @PathVariable("id")Long id){

        return ResponseEntity.ok(replyService.delete(id));
    }

    @GetMapping("/board/{id}/reply")
    public ResponseEntity write(@AuthenticationPrincipal(expression = "account") Account account,
                                      ReplySearchDto request){
        return ResponseEntity.ok().build();
    }




}
