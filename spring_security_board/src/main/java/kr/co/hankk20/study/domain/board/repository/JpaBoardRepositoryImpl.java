package kr.co.hankk20.study.domain.board.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import kr.co.hankk20.study.domain.account.QAccount;
import kr.co.hankk20.study.domain.account.QAccountDto;
import kr.co.hankk20.study.domain.account.code.AccountType;
import kr.co.hankk20.study.domain.board.Board;
import kr.co.hankk20.study.domain.board.QBoard;
import kr.co.hankk20.study.domain.board.QBoardLike;
import kr.co.hankk20.study.domain.board.QReply;
import kr.co.hankk20.study.domain.board.dto.BoardDto;
import kr.co.hankk20.study.domain.board.dto.QBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.LockModeType;
import java.util.List;

import static com.querydsl.core.types.ExpressionUtils.count;

public class JpaBoardRepositoryImpl extends QuerydslRepositorySupport implements JpaBoardRepository {

    private QBoard board = QBoard.board;
    private QAccount account = QAccount.account;

    public JpaBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public BoardDto findBoard(Long id, Long accountId){
        return getBoardDtoQuery(accountId)
                .where(board.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<BoardDto> findAllBoard(Long accountId, Predicate predicate, Pageable pageRequest){
        JPQLQuery<Board> countQuery = createCountQuery(predicate);
        JPQLQuery<BoardDto> query = getBoardDtoQuery(accountId)
                .where(predicate);

        JPQLQuery<BoardDto> boardDtoJPQLQuery = getQuerydsl().applyPagination(pageRequest, query);
        return PageableExecutionUtils.getPage(boardDtoJPQLQuery.fetch(), pageRequest, countQuery::fetchCount);
    }

    /**
     * 게시판 Count Query
     * Outer Join은 갯수에 상관없으니 제외
     * @param predicate
     * @return
     */
    private JPQLQuery<Board> createCountQuery(Predicate predicate){
        return from(board)
                .join(board.account, account)
                .where(predicate);
    }

    /**
     * 게시판 조회 Query
     * 작성자를 알기 위해 Account와 조인
     * 댓글 갯수를 알기 위해 Reply Sub Query로 가져온다.
     * 좋아요 갯수를 알기 위해 BoardLike 그리고 BoardLike에서 Board.id로 그룹
     * 조회 당사자의 좋아요 여부를 확인 하기위해 용도로 BoardLike Join
     *
     * -- 생각해볼 문제 --
     * 비즈니스 로직이 없는 단순 조회에서 DTO 변환은 어디서 하는게 좋을까?
     *  1) Board Entity를 리턴하여 Service에서 DTO로 변환
     *  2) Repository에서 Projection을 사용 하여 바로 DTO로 변환
     *
     *  == 나의 의견 ==
     *  Entity가 필요한 경우는 해당 Entity의 값을 변경할때인데, 단순조회에서는 Entity 값을 변경 할 일이 없다.
     *  따라서 사용하지 않을 Entity를 조회하여 1차 캐쉬에 저장하는 오버헤드를 발생 시키는 것 보다.
     *  쿼리에서 바로 Projection으로 받아서 리턴하는 방법이 나은것 같아서 찿아보니 글이 있네
     *  참고 : https://thorben-janssen.com/entities-dtos-use-projection/
     * @param id
     * @return
     */
    private JPQLQuery<BoardDto> getBoardDtoQuery(Long id){

        //TODO ANONYMOUS 일때 처리방법 고민
        long idValue = id == null?0L:id.longValue();

        QBoardLike like = QBoardLike.boardLike;
        QBoardLike myLike = new QBoardLike("myLike");
        QReply reply = QReply.reply;

        CaseBuilder b = new CaseBuilder();
        b.when(account.accountType.eq(AccountType.LESSEE))
                .then("sss")
                .otherwise(account.accountType.stringValue()).as("sss");
        return from(board)
                .join(board.account, account)
                .leftJoin(board.boardLikes, like)
                .leftJoin(board.boardLikes, myLike)
                .on(myLike.account.id.eq(idValue))
                .select(new QBoardDto(board.id,
                        new QAccountDto(account.id,
                                account.accountId,
                                account.nickname,
                                account.accountType,
                                new CaseBuilder().
        when(account.accountType.eq(AccountType.LESSOR))
                .then("sss")
                .otherwise(account.accountType.stringValue()),

                                account.quit),
                        board.title,
                        board.contents,
                        board.createDate,
                        board.updateDate,
                        like.id.count(),
                        JPAExpressions.select(count(reply.id))
                                .from(reply)
                                .where(reply.board.id.eq(board.id)),
                        myLike.id.isNotNull()
                ))
                .groupBy(board.id);
    }

    @Override
    public List<BoardDto> getBoardCustom(Long id, QBean bean){
        QBoard qBoard = QBoard.board;
        return from(qBoard)
                .select(bean)
                .fetch();
    }

    @Override
    public QBean<BoardDto> custom1(){
        QBoard board = QBoard.board;
        return Projections.bean(BoardDto.class, board.id, board.title);
    }

    @Override
    public Board getBoardLastId(){
//        return from(QBoard.board)
//                .orderBy(QBoard.board.id.desc())
//                .limit(1)
//                .fetchOne();

        return (Board) getQuerydsl().createQuery()
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .from(QBoard.board)
                .where(QBoard.board.id.eq(1L))
                .fetchOne();
    }

}
