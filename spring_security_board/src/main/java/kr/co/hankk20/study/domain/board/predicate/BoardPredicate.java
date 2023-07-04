package kr.co.hankk20.study.domain.board.predicate;

import com.querydsl.core.types.Predicate;
import kr.co.hankk20.study.domain.board.QBoard;

public class BoardPredicate {

    public static Predicate eqAccountId(Long accountId){
        if(accountId != null && accountId.longValue() != 0) {
            return QBoard.board.account.id.eq(accountId);
        }
        return null;
    }
}
