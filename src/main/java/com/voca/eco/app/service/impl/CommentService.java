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

    // Controller랑 Reposioty랑 Serivce중에서 interface를 작성할 때의 순서
    // 1. 값을 받는 컨트롤러는 정해져있다 > DTO에 어떤값을 보내서 Service로 보낼지 정하기
    // 2. 그 값을 파라미터로 가지는 Serivce Interface 구현하기
    // 3. 그 값을 파라미터 혹은 Entity에 값을 추가하여 Repository에 interface로 구현
    /*
         1. Controller

         public String noticeInfo(HttpServletRequest request, HttpSession session) throws Exception
         여기서는 거의 모든 상황에서 rquest 혹은 @RequestParam으로 값을 받고 session or token으로 userId를 받는
         상황이 많이 나온다

         >> request로 받은 값을 Service에 넘겨준다고 생각하고 Service Interface를 작성한다
         request = noticeSeq치면
         public List<CommentDTO> commentList(Long noticeSeq, CommentDTO pDTO)

         >> Entity에 request로 받은 값을 넣어서 DB를 조회한다
         Repository랑 Service의 반환값은 거의 비슷하다

         IRepo > List<CommentEntity> commentList(CommentEntity pEntity)
         Entity는 DB에 접근하기위한 매개체임을 잊지말것

     */


    /*
     댓글 보기
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentList(CommentDTO pDTO) {
        log.info("[ 서비스 ] : 댓글보기 시작");

        // 1. 파라미터로 받은 값을 log를 찍어 제대로 넘어왔는지 확인
        log.info("pDTO의 noticeSeq값 : " + pDTO.noticeSeq());

        // 2. 받은 값을 가지고 DB를 조회하기 위해서 DTO의 값을 Entity로 변환
        CommentEntity cEntity = CommentEntity.builder()
                .noticeSeq(pDTO.noticeSeq())
                .build();

        // 3. Entity를 Repository에 정의해둔 함수에 넣어 DB에서 값을 조회한 뒤 돌려줄 객체에 담기
        List<CommentEntity> rList = commentRepository.findAllByNoticeSeqAndOrderByCommentSeqDesc();


        // 4. Controller와 Service간의 데이터이동은 무조건 DTO로 해야한다
        // Entity의 존재의의는 DB에 직접 관여하기 위한 매개체일 뿐이다.
        // ObjectMapeer를 사용해서 Entity를 DTO로 변환
        List<CommentDTO> nList = new ObjectMapper().convertValue(rList,
                new TypeReference<>() {
                });

        // return값이 int 혹은 boolean의 값이면 검증하기 쉽다
        log.info("[ 서비스 ] : 댓글보기 끝");

        // 5. Controller로 값을 반환
        return nList;
    }

    /*
     댓글 작성
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertComment(Long noticeSeq,
            String userId,
            String comment) throws Exception {

        log.info("[ 서비스 ] : 댓글 작성하기 시작");

        // 1. 값 제대로 받았는지 확인
        log.info("noticeSeq" +noticeSeq);
        log.info("userId" + userId);
        log.info("comment" + comment);


        // 2. 받은 값을 엔터티(DB)에 저장하기
        commentRepository.save(
                CommentEntity.builder()
                        .noticeSeq(noticeSeq)
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
    public void updateComment(final String userId,
            final String comment,
            final Long noticeSeq,
            final Long commentSeq
    ) throws Exception {

        // 1. 값을 제대로 받았는지 확인하기
        log.info("[ 서비스 ] : 댓글 수정하기 시작");
        log.info("commentSeq" +commentSeq);
        log.info("noticeSeq" +noticeSeq);
        log.info("userId" + userId);
        log.info("comment" + comment);

        // 2. Repository에서 댓글번호로 조회해서 엔터티에 담기
        CommentEntity rEntity = commentRepository.findByCommentSeq(commentSeq);

        // 3. 받은 댓글 내용을 엔터티에 보내 save로 저장하여 변경하기
        commentRepository.save(rEntity.updateComment(comment));

        log.info("[ 서비스 ] : 댓글 수정하기 끝");


    }

    /*
     댓글 삭제
     */
    @Override
    public void deleteComment(final Long commentSeq,
            final String userId,
            final Long noticeSeq) throws Exception {

        log.info("[ 서비스 ] :  댓글삭제 시작!" );

        log.info("noticeSeq : " + noticeSeq);
        log.info("comment : " + commentSeq);
        log.info("userId : " + userId);

        commentRepository.deleteByNoticeSeqAndCommentSeqAndUserId(commentSeq, noticeSeq, userId);

        log.info("[ 서비스 ] :  댓글삭제 끝!" );

    }
}
