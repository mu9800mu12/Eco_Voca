package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.CommentEntity;
import java.util.List;
import javax.xml.stream.events.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * 댓글 리스트 보여주기
     */
    List<CommentEntity> findAllByOrderByCommentSeqDese();

    /**
     * 댓글 작성하기
     */
    /**
     * 댓글 수정하기
     */
    CommentEntity findByCommentSeq(Long commentSeq);
    /**
     *  댓글 삭제하기
     */
    CommentEntity deleteByCommentSeq(Long commentSeq);
}
