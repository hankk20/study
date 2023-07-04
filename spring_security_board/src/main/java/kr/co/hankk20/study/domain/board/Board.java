package kr.co.hankk20.study.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.commons.AuditProperties;
import kr.co.hankk20.study.exception.CustomBadRequestException;
import kr.co.hankk20.study.exception.CustomSystemException;
import lombok.*;
import org.hibernate.envers.NotAudited;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true) @ToString(onlyExplicitlyIncluded = true)
@Entity @Table(name="board")// @Audited
public class Board extends AuditProperties {

    @ToString.Include
    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "board_seq", sequenceName = "bard_seq", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq") @Column(name = "id", nullable = false)
    private Long id;

    //@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @NotAudited
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * 게시물에 댓글이 많을 경우 한번에 많은 댓글을 로드 하지 못하도록 Getter를 제외한다.
     * Board에서 댓글을 참조 해야 되는 로직은 없기 때문에 Getter가 없어도 무방하다.
     * 댓글 조회는 API를 별도로 만들어 Paging 처리하여 제공하는 편이 낫다.
     */
    //@Getter(AccessLevel.NONE)
    @NotAudited
    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    /**
     * Getter를 제외 함.
     * 좋아요가 많아지고 실수로 boardLikes를 Get 하면 해당 Board의 BoardLike를 Database에서 전체 로드 하기 때문에 성능에 문제가 있을 가능성이 있다.
     * 좋아요는 갯수만 가지고 있으면 되기 때문에 Getter를 제외한다.
     * 단방향으로 BoardLike에만 연관관계를 맵핑 시켜 놔도 되지만
     * 객체지향적으로 보면 좋아요는 보드에 추가 되는게 맞기 때문에
     * 양방향으로 설정을 하여 Board에 좋아요를 추가하면 저장하도록 했다.
     */
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @NotAudited
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE})
    private List<BoardLike> boardLikes = new ArrayList<>();

    @ToString.Include
    @Setter
    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @ToString.Include
    @Setter
    @Column(name = "contents", nullable = false, length = 4000)
    private String contents;

    @Setter
    private LocalDateTime deletedDate;

    @Setter
    private boolean deleted;

    @ToString.Include @Setter
    private int count;

    protected Board(){}

    public Board(Account account, String title, String contents) {
        this.account = account;
        this.title = title;
        this.contents = contents;
    }

    public void addReply(Reply reply){
        reply.setBoard(this);
        replies.add(reply);
    }

    public void setReplys(List<Reply> replies){
        this.replies = replies;
    }

    public void deleteAllReply(){
        if(replies != null) {
            replies.clear();
        }
    }


    public void addLike(Account liker){
        boardLikes.add(new BoardLike(this, liker));
    }

    /**
     * 수정이나 삭제 읽기 권한 검사를 지끔까지 개발 해왔던 방식으로 Service에서 처리를 할 수도 있었으나 Board 자체에서 권한 검사로직을 두기로 했다.
     * 다른 Service에서 Board를 사용 하더라도 BoardService를 참조 하지 않아도 되며,
     * 추후 Board 종류가 다양해지면 현재 Board는 Super 클래스로 두고 Board 종류별로 Subtype을 만들고 checkPermisson을 각 Board에 맞게 재구성을 해도 된다.
     * @param writer
     */
    public void checkPermission(Account writer){
        if(!equalsWriter(writer)){
            throw new CustomSystemException("권한이 없습니다.", HttpStatus.FORBIDDEN); //TODO Exception 정의하자
        }
    }

    public void delete(){
        if(deleted){
            throw new CustomBadRequestException("이미 삭제된 게시물 입니다.");
        }
        deleted = true;
        deletedDate = LocalDateTime.now();
    }

    public boolean equalsWriter(Account writer){
        return this.account.equals(writer);
    }

    public void addCount(){
        count++;
    }
}
