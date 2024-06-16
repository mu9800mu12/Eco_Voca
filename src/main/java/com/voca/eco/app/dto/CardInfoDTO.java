package com.voca.eco.app.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardInfoDTO {
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvc;
}
