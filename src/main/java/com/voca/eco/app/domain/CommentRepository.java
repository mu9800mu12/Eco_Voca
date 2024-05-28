package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * 댓글 리스트 보여주기
     */

    // JPA에서는 find(delete, insert, update) By 뒤에는 기준을 작성하고 And를 써서 정렬같은 부가작업을 수행
    List<CommentEntity> findAllByOrderByCommentSeqDesc();

    // 반환값이 없다(List or Entity를 반환하지 않는 경우)는 void말고 int를 쓰자 성공 실패 여부를 반환하자

    /**
     * 댓글 작성하기
     */

    /**
     * 댓글 수정하기
     */
    CommentEntity findByCommentSeq(Long commentSeq);

    // 게시글 번호화 댓글번호로 댓글 호출하기
    List<CommentEntity> findAllByNoticeSeq(Long noticeSeq);

    /**
     * 댓글 삭제하기
     */
    void deleteByNoticeSeqAndCommentSeqAndUserId(Long commentSeq, Long noticeSeq, String userId);
}
