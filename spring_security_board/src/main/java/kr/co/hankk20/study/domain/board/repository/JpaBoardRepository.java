package kr.co.hankk20.study.domain.board.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.QBean;
import kr.co.hankk20.study.domain.board.Board;
import kr.co.hankk20.study.domain.board.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface JpaBoardRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BoardDto findBoard(Long id, Long accountId);

    Page<BoardDto> findAllBoard(Long accountId, Predicate predicate, Pageable pageRequest);

    List<BoardDto> getBoardCustom(Long id, QBean bean);

    QBean<BoardDto> custom1();


    Board getBoardLastId();
}
