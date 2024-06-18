package com.voca.eco.app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardInfoDTO {

    private String userId;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvc;


}
