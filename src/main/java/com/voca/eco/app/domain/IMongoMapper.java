package com.voca.eco.app.domain;

import com.voca.eco.app.dto.ChatDTO;

public interface IMongoMapper {

    /**
     *
     * 채팅 데이터 데이터 저장하기
     *
     * @param pDTO 저장될 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     */
    int insertData(ChatDTO pDTO, String colNm) throws Exception;

}
