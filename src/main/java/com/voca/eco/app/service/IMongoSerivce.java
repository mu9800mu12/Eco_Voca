package com.voca.eco.app.service;

import com.voca.eco.app.dto.ChatDTO;

public interface IMongoSerivce {

    /*
     * 채팅 데이터 저장하기
     */
    int insertChat(ChatDTO pDTO) throws Exception;

}
