//package com.voca.eco.app.service.impl;
//
//import com.example.demo.entity.CardInfoEntity;
//import com.voca.eco.app.domain.CardInfoRepository;
//import com.voca.eco.app.dto.CardInfoDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class CardInfoService {
//
//    private final CardInfoRepository cardInfoRepository;
//
//    public CardInfoDTO saveCardInfo(CardInfoDTO cardInfoDTO) {
//        CardInfoEntity cardInfoEntity = CardInfoEntity.builder()
//                .cardNumber(cardInfoDTO.getCardNumber())
//                .cardHolder(cardInfoDTO.getCardHolder())
//                .expiryDate(cardInfoDTO.getExpiryDate())
//                .cvc(cardInfoDTO.getCvc())
//                .build();
//
//        cardInfoEntity = cardInfoRepository.save(cardInfoEntity);
//
//        return CardInfoDTO.builder()
//                .cardNumber(cardInfoEntity.getCardNumber())
//                .cardHolder(cardInfoEntity.getCardHolder())
//                .expiryDate(cardInfoEntity.getExpiryDate())
//                .cvc(cardInfoEntity.getCvc())
//                .build();
//    }
//}
