package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.IMongoMapper;
import com.voca.eco.app.dto.ChatDTO;
import com.voca.eco.app.service.IMongoSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MongoService implements IMongoSerivce {

    private final IMongoMapper mongoMapper;

    @Override
    public int insertChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : 고객센터 채팅 시작");

        String colNm = "Chat";

      int res = mongoMapper.insertData(pDTO, colNm);
//     todo 래퍼지토리로 변경

        log.info(this.getClass().getName() + "[서비스] : 고객센터 채팅 끝");

        return res;
    }
}
