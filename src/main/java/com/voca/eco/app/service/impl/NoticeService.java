package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.NoticeRepository;
import com.voca.eco.app.dto.NoticeDTO;
import com.voca.eco.app.service.INoticeService;
import java.util.List;
import java.util.ServiceLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService implements INoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> getNoticeList() {

        return null;
    }

    @Override
    public NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception {
        return null;
    }

    @Override
    public void updateNoticeInfo(Long noticeSeq, String title, String contents,
            String userId, String noticeYn) {

    }

    @Override
    public void deleteNoticeInfo(Long noticeSeq) throws Exception {

    }

    @Override
    public void insertNoticeInfo(String title, String noticeYn, String contents,
            String userId) throws Exception {

    }
}
