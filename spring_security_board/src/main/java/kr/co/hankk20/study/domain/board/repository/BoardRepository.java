package kr.co.hankk20.study.domain.board.repository;

import kr.co.hankk20.study.domain.board.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaBoardRepository {

    @Override
    @EntityGraph(attributePaths = {"replies", "replies.account", "account"})
    List<Board> findAll();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Board findGetById(Long id);
}
