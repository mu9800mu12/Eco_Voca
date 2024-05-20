package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.CommentRepository;
import com.voca.eco.app.domain.Entity.CommentEntity;
import com.voca.eco.app.dto.CommentDTO;
import com.voca.eco.app.service.ICommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    /*
     댓글 보기
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentList() {
        log.info("[ 서비스 ] : 댓글보기 시작");

        List<CommentEntity> rList = commentRepository.findAllByOrderByCommentSeqDese();

        List<CommentDTO> nList = new ObjectMapper().convertValue(rList,

                new TypeReference<>() {
                });
        log.info("[ 서비스 ] : 댓글보기 끝");

        return nList;
    }

    /*
     댓글 작성
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertComment(Long commentSeq,
            String userId,
            String comment) throws Exception {

        log.info("[ 서비스 ] : 댓글 작성하기 시작" );
        log.info("commentSeq" +commentSeq);
        log.info("userId" + userId);
        log.info("comment" + comment);

        commentRepository.save(
                CommentEntity.builder()
                        .comment(comment)
                        .userId(userId)
                        .build()

        );

        log.info("[ 서비스 ] : 댓글 작성하기 끝" );

    }

    /*
     댓글 수정하기
     */
    @Override
    public void updateComment(Long commentSeq,
            String userId,
            String comment) throws Exception {

        log.info("[ 서비스 ] : 댓글 수정하기 시작");
        log.info("commentSeq" +commentSeq);
        log.info("userId" + userId);
        log.info("comment" + comment);

        CommentEntity rEntity = commentRepository.findByCommentSeq(commentSeq);

        commentRepository.save(rEntity.updateComment(comment));

        log.info("[ 서비스 ] : 댓글 수정하기 끝");


    }

    /*
     댓글 삭제
     */
    @Override
    public void deleteComment(Long commentSeq) throws Exception {

        log.info("[ 서비스 ] :  댓글삭제 시작!" );

        log.info("comment : " +commentSeq);

        commentRepository.deleteByCommentSeq(commentSeq);

        log.info("[ 서비스 ] :  댓글삭제 끝!" );

    }
}
