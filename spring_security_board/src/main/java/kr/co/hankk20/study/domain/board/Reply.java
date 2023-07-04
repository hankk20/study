package kr.co.hankk20.study.domain.board;

import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.commons.AuditProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity @Table(name="reply")
public class Reply extends AuditProperties {

    @Setter
    @EqualsAndHashCode.Include
    @SequenceGenerator(name="reply_seq", sequenceName = "reply_seq", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = "reply_seq") @Column(name = "id", nullable = false)
    private Long id;

    @EqualsAndHashCode.Include
    @Setter
    @Column(name = "contents", nullable = false, length = 4000)
    private String contents;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    protected Reply(){}

    public Reply(Account account, String contents){
        this.account = account;
        this.contents = contents;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
