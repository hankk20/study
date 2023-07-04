package kr.co.hankk20.study.domain.board.service;

import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.board.Board;
import kr.co.hankk20.study.domain.board.BoardLike;
import kr.co.hankk20.study.domain.board.predicate.BoardLikePredicate;
import kr.co.hankk20.study.domain.board.repository.BoardLikeRepository;
import kr.co.hankk20.study.exception.CustomBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardService boardService;

    public long like(long boardId, Account account){
        if(alreadyLike(boardId, account)){
            throw new CustomBadRequestException("이미 좋아요를 한 게시물 입니다.");
        }
        return addLike(boardId, account);
    }

    public boolean alreadyLike(long boardId, Account account){
        return boardLikeRepository.exists(BoardLikePredicate.compositeKey(boardId, account));
    }

    public long unlike(long boardId, Account account){
        findBoardLike(boardId, account.getId())
                .ifPresent(boardLikeRepository::delete);
        return boardId;
    }

    public Optional<BoardLike> findBoardLike(long id, long accountId){
        return boardLikeRepository.findOne(BoardLikePredicate.compositeKey(id, accountId));
    }

    private long addLike(long boardId, Account account){
        Board board = boardService.findOrElseThrow(boardId);
        board.addLike(account);

        return board.getId();
    }

}
