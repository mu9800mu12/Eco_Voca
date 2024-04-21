package com.voca.eco.app.service;

import com.voca.eco.app.dto.NoticeDTO;
import java.util.List;

public interface INoticeService {


    /**
     * 공지사항 전체 가져오기
     */
    List<NoticeDTO> getNoticeList();


}
