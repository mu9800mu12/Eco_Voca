package com.voca.eco.app.domain;

import com.voca.eco.app.dto.CardInfoDTO;

public interface ICardInfoMongo {

    //카드 정보 저장하기
    int insertCard(CardInfoDTO pDTO, String colNm) throws Exception;
    
    //카드 정보 가져오기
    CardInfoDTO getCardInfo(String userId) throws Exception;





}
