package com.voca.eco.app.service;

import com.voca.eco.app.dto.NoticeDTO;
import java.util.List;

public interface INoticeService {


    /**
     * 게시판 전체보기
     */
    List<NoticeDTO> getNoticeList();

    /**
     * 게시판 상세보기
     *
     * @param pDTO 공지사항 상세 가져오기 위한 정보
     * @param type 조회수 증가여부(true : 증가, false : 증가안함
     */
    NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception;

    /**
     * 게시판 작성
     * @param title
     * @param noticeYn
     * @param contents
     * @param userId
     * @throws Exception
     */
    void insertNoticeInfo(  final String title,
            final String noticeYn,
            final String contents,
            final String userId) throws Exception;

    /**
     * 게시판 업데이트
     *
     */
    void updateNoticeInfo(final Long noticeSeq,
            final String title,
            final String contents,
            final String userId,
            final String noticeYn);
    /**
     * 해당 공지사항 삭제
     */
    void deleteNoticeInfo(final Long noticeSeq) throws Exception;
}
