package kr.co.hankk20.study.domain.board;

import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.commons.AuditProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity @Table(name = "board_like")
public class BoardLike extends AuditProperties {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    protected BoardLike(){}

    public BoardLike(Board board, Account account) {
        this.board = board;
        this.account = account;
    }

}
