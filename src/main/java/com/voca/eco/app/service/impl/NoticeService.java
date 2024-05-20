package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.Entity.NoticeEntity;
import com.voca.eco.app.domain.NoticeRepository;
import com.voca.eco.app.dto.NoticeDTO;
import com.voca.eco.app.service.INoticeService;
import java.util.List;
import java.util.ServiceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService implements INoticeService {

    private final NoticeRepository noticeRepository;

    /**
     *  게시판 전체보기
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<NoticeDTO> getNoticeList() {

        log.info(this.getClass().getName() + "[서비스] : .getNoticeList 시작!");

        // 공지사항 전체 리스트 조회하기
        List<NoticeEntity> rList = noticeRepository.findAllByOrderByNoticeSeqDesc();

        // 엔티티의 값들을 DTO에 맞게 넣어주기
        List<NoticeDTO> nList = new ObjectMapper().convertValue(rList,

                new TypeReference<>() {
                });


        log.info(this.getClass().getName() + "[서비스] : .getNoticeList 끝!");

        return nList;
    }

    /**
     * 게시판 상세보기
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : .getNoticeInfo 시작!");

        NoticeEntity rEntity = noticeRepository.findByNoticeSeq(pDTO.noticeSeq());
        rEntity.addReadCount();
        if (type) {
            noticeRepository.save(rEntity);
        }

        NoticeDTO rDTO = new ObjectMapper().convertValue(rEntity, NoticeDTO.class);

        log.info(this.getClass().getName() + "[서비스] : .getNoticeInfo 끝!");

        return rDTO;
    }

    /**
     * 게시판 작성하기
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertNoticeInfo(
            final String title,
            final String noticeYn,
            final String contents,
            final String createdByUserId
    ) throws Exception {
        log.info(this.getClass().getName() + "[서비스] : .InsertNoticeInfo Start!");
        log.info("title : " + title);
        log.info("noticeYn : " + noticeYn);
        log.info("contents : " + contents);
        log.info("userId : " + createdByUserId);

        // 빌더로 올리고 바로 저장 떄려버림 값이 없으면 save가오류를 잡아줌
        noticeRepository.save(
                NoticeEntity.builder()
                        .title(title)
                        .noticeYn(noticeYn)
                        .contents(contents)
                        .userId(createdByUserId)
                        .regId(createdByUserId)
                        .chgId(createdByUserId)
                        .build()
        );

        log.info(this.getClass().getName() + "[서비스] : .InsertNoticeInfo End!");

    }

    /**
     * 게시판 수정하기
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNoticeInfo(
            final Long noticeSeq,
            final String title,
            final String contents,
            final String userId,
            final String noticeYn
    ) {

        log.info(this.getClass().getName() + "[서비스] : .updateNoticeInfo Start!");

        log.info("noticeSeq : " + noticeSeq);
        log.info("title : " + title);
        log.info("noticeYn : " + noticeYn);
        log.info("contents : " + contents);
        log.info("userId : " + userId);

        NoticeEntity rEntity = noticeRepository.findByNoticeSeq(noticeSeq);

        noticeRepository.save(rEntity.updateNoticeInfo(title, contents, noticeYn));

    }

    /**
     * 게시판 삭제하기
     */
    @Override
    public void deleteNoticeInfo(final Long noticeSeq) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : .deleteNoticeInfo Start!");

        log.info("noticeSeq : " + noticeSeq);

        // 데이터 수정하기
        noticeRepository.deleteById(noticeSeq);

        log.info(this.getClass().getName() + "[서비스] : .deleteNoticeInfo End!");
    }

}
