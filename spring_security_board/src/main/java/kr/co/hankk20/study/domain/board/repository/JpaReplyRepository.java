package kr.co.hankk20.study.domain.board.repository;

import kr.co.hankk20.study.domain.board.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;

public interface JpaReplyRepository {
    Page<ReplyDto> findAll(long id, QPageRequest pageRequest);
}
