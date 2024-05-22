package com.voca.eco.app.service;

import com.voca.eco.app.dto.CommentDTO;
import java.util.List;

public interface ICommentService {

    /**
     * 댓글 전체보기
     */

    List<CommentDTO> getCommentList();
    /**
     * 댓글 작성하기
     */
    void insertComment(final Long commentSeq,
            final String userId,
            final String comment) throws Exception;
    /**
     * 댓글 수정하기
     */
    void updateComment(final Long commentSeq,
            final String userId,
            final String comment,
            final Long noticeSeq) throws Exception;
    /**
     * 댓글 삭제하기
     */
    void deleteComment(final Long commentSeq,
            final String userId) throws Exception;
}
