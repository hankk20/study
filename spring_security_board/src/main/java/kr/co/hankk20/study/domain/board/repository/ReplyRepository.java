package kr.co.hankk20.study.domain.board.repository;

import kr.co.hankk20.study.domain.board.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReplyRepository extends JpaRepository<Reply, Long>, QuerydslPredicateExecutor<Reply>, JpaReplyRepository {
    void deleteByBoardId(long id);
}
