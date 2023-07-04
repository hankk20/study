package kr.co.hankk20.study.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.hankk20.study.domain.account.AccountDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString @NoArgsConstructor
@Setter
public class BoardDto implements Serializable {
    private long id;
    private AccountDto account;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private long likeCount;
    private long replyCount;
    private boolean liked;

    @QueryProjection
    public BoardDto(long id, AccountDto account, String title, String contents, LocalDateTime createDate, LocalDateTime updateDate, long likeCount, long replyCount, boolean liked) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.contents = contents;
        this.likeCount = likeCount;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.replyCount = replyCount;
        this.liked = liked;
    }

}
