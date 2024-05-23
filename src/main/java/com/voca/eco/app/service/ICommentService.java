package com.voca.eco.app.service;

import com.voca.eco.app.dto.CommentDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ICommentService {

    /**
     * void로 메서드를 실행 시켜도 좋지만
     *
     * int 로 값을 받아서 성공하면 1 실패하면 0 을 반환하는 코드도 있다.
     */


    /**
     * 댓글 전체보기
     */

    List<CommentDTO> getCommentList(CommentDTO pDTO);

    /**
     * 댓글 수정하기
     */
    void updateComment(final String userId,
            final String comment,
            final Long noticeSeq,
            final Long commentSeq) throws Exception;

    /**
     * 댓글 작성
     */
    @Transactional(rollbackFor = Exception.class)
    void insertComment(Long noticeSeq,
            String userId,
            String comment) throws Exception;

    /*
         댓글 수정하기
         */

    /**
     * 댓글 삭제하기
     */
    void deleteComment(final Long commentSeq,
            final String userId,
            final Long noticeSeq) throws Exception;
}
