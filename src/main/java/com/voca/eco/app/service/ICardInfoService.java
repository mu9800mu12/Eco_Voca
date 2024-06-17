package com.voca.eco.app.service;

import com.voca.eco.app.dto.CardInfoDTO;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ICardInfoService {


    // OCR 카드 정보 가져오기
    List<CardInfoDTO> cardOcr(BufferedImage highResolutionImage) throws Exception;

    // 카드 정보 MongoDB에 저장하기
    int mongoTest(CardInfoDTO pDTO) throws Exception;
}
