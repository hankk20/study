package kr.co.hankk20.study.domain.board.controller;

import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.board.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/board/{id}/like")
    public ResponseEntity<Long> like(@AuthenticationPrincipal(expression = "account") Account account, @PathVariable("id")long id){
        return ResponseEntity.ok(boardLikeService.like(id, account));
    }
    @DeleteMapping("/board/{id}/like")
    public ResponseEntity<Long> unlike(@AuthenticationPrincipal(expression = "account") Account account, @PathVariable("id")long id){
        return ResponseEntity.ok(boardLikeService.unlike(id, account));
    }
}
