package kr.co.hankk20.study.domain.board.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import kr.co.hankk20.study.domain.account.QAccount;
import kr.co.hankk20.study.domain.account.QAccountDto;
import kr.co.hankk20.study.domain.board.QReply;
import kr.co.hankk20.study.domain.board.Reply;
import kr.co.hankk20.study.domain.board.dto.QReplyDto;
import kr.co.hankk20.study.domain.board.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.support.PageableExecutionUtils;

public class JpaReplyRepositoryImpl extends QuerydslRepositorySupport implements JpaReplyRepository{

    private QReply reply = QReply.reply;

    public JpaReplyRepositoryImpl() {
        super(Reply.class);
    }

    @Override
    public Page<ReplyDto> findAll(long id, QPageRequest pageRequest){
        BooleanExpression predicate = reply.board.id.eq(id);

        QAccount account = QAccount.account;

        JPQLQuery<Reply> defaultQuery = from(reply)
                .join(reply.account, account)
                .where(predicate);

        JPQLQuery<ReplyDto> query = defaultQuery
                .select(new QReplyDto(
                        reply.id,
                        new QAccountDto(account.id,
                                account.accountId,
                                account.nickname,
                                account.accountType,
                                account.accountType.stringValue(),
                                account.quit),
                        reply.contents,
                        reply.createDate,
                        reply.updateDate
                ));

        JPQLQuery<ReplyDto> pageQuery = getQuerydsl().applyPagination(pageRequest, query);

        return PageableExecutionUtils.getPage(pageQuery.fetch(), pageRequest, defaultQuery::fetchCount);

    }

    protected JPQLQuery<Reply> createCountQuery(Predicate predicate){
        JPQLQuery<Reply> query = from(reply);
        if(predicate != null){
            query = query.where(predicate);
        }
        return query;
    }
}
