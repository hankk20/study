package kr.co.hankk20.study.domain.board.repository;

import kr.co.hankk20.study.domain.board.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, QuerydslPredicateExecutor<BoardLike> {

}
