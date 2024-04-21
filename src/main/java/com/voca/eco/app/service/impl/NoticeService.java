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
}
