package kr.co.hankk20.study.domain.board.service;

import kr.co.hankk20.study.domain.board.Board;
import kr.co.hankk20.study.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class LockService {


    private final BoardRepository boardRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Board getBoardLock(){
        return boardRepository.getBoardLastId();
    }
}
