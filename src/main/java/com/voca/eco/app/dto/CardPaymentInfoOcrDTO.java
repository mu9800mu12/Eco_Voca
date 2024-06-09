package com.voca.eco.app.dto;

import java.util.Date;

public record CardPaymentInfoOcrDTO(
        String cardNumber, // 카드 번호
        String userId, // 사용자 ID
        String expirationDate, // 유효 기간
        String securityCode, // 보안 코드
        String cardholderName, // 카드 소유자 이름
        String billingAddress, // 청구지 주소
        Date createdAt, // 생성 일자
        Date updatedAt // 수정 일자

       // boolean isActive // 활성화 여부
) {}
