package kr.co.hankk20.study.domain.board.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import kr.co.hankk20.study.domain.account.Account;
import kr.co.hankk20.study.domain.board.QBoardLike;

public class BoardLikePredicate {

    public static BooleanExpression compositeKey(long boardId, Account account){
       return compositeKey(boardId, account.getId());
    }

    public static BooleanExpression compositeKey(long boardId, long accountId){
        QBoardLike boardLike = QBoardLike.boardLike;
        return boardLike.account.id.eq(accountId).and(boardLike.board.id.eq(boardId));
    }
}
