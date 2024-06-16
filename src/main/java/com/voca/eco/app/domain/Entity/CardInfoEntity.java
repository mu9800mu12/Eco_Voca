package com.example.demo.entity;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 규약을 따르기 위해 생성자 추가
public class CardInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvc;

    @Builder
    public CardInfoEntity(Long id, String cardNumber, String cardHolder, String expiryDate, String cvc) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
    }
}
